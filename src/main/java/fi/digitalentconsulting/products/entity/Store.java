package fi.digitalentconsulting.products.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Store {
	private static Logger LOGGER = LoggerFactory.getLogger(Store.class);

	@Id @GeneratedValue
	private Long id;
	private String name;
	private String address;
	@JsonIgnore
    @OneToMany(mappedBy = "store", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	private List<Inventory> inventory = new ArrayList<>();
    
    public Store() {
    }
        
	public Store(Long id) {
		this.id = id;
	}

	public Store(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Inventory> getInventory() {
		return inventory;
	}
	public void addProducts(Product product, int count) {
		Inventory existing = inventory.stream()
				.filter(item -> item.getProduct().getId().equals(product.getId()))
				.findAny().orElse(null);
		if (existing != null) {
			existing.addToCount(count);
		} else {
			Inventory newItem = new Inventory();
			newItem.setStore(this);
			newItem.setProduct(product);
			newItem.setCount(count);
			inventory.add(newItem);
			LOGGER.info("New inventory item created: {}", newItem);			
		}
	}
	
	public void removeProducts(Product product, int count) throws NoSuchElementException {
		Inventory existing = inventory.stream()
				.filter(item -> item.getProduct().getId().equals(product.getId()))
				.findAny().orElseThrow();
		existing.removeFromCount(count);
	}
}
