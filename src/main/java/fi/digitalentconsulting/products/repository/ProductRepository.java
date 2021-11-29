package fi.digitalentconsulting.products.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.digitalentconsulting.products.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
	List<Product> findAll();
	List<Product> findAllByPriceGreaterThan(Double price);
	List<Product> findAllByNameContainingIgnoreCase(String name);
	List<Product> findAllByNameContainingIgnoreCaseAndPriceGreaterThan(String name, Double price);
	
}
