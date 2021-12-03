package fi.digitalentconsulting.products.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Inventory {
	// Keeping things simple, instead of a multicolumn key
	@Id @GeneratedValue
	Long id;
	
	@ManyToOne
	@JoinColumn(name="product")
	private Product product;
	
	@ManyToOne()
	@JoinColumn(name="store")
	private Store store;
	private int count;

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void addToCount(int count) {
		// assert count > 0 ??
		this.count += count;
	}
	public void removeFromCount(int count) {
		// assert count > 0  && this.count will not be negative
		this.count -= count;
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", product=" + product.getName() + ", store=" + store.getName() + ", count=" + count + "]";
	}
	
	
}
