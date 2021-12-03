package fi.digitalentconsulting.products.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Product {
	@Id @GeneratedValue
	private Long id;
	@NotBlank(message = "Name can't be blank")
	private String name;
	@NotBlank(message = "Description can't be blank")
	private String description;
	@Min(value=0, message="Cannot be a negative price")
	private Double price;
	
	// For Optimistic lock
	@Version
	Long version;
	
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	private List<Inventory> inventory = new ArrayList<>();
	
	public Product() {}
	
	public Product(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public Product(@NotBlank(message = "Name can't be blank") String name,
			@NotBlank(message = "Description can't be blank") String description,
			@Min(value = 0, message = "Cannot be a negative price") Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}

	
}
