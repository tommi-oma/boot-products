package fi.digitalentconsulting.products.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import fi.digitalentconsulting.products.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
	List<Product> findAll();
	@Lock(LockModeType.OPTIMISTIC)
	List<Product> findAllByPriceGreaterThan(Double price);
	@Lock(LockModeType.PESSIMISTIC_READ)
	List<Product> findAllByNameContainingIgnoreCase(String name);
	List<Product> findAllByNameContainingIgnoreCaseAndPriceGreaterThan(String name, Double price);
	
}
