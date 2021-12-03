package fi.digitalentconsulting.products;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import fi.digitalentconsulting.products.entity.Product;
import fi.digitalentconsulting.products.entity.Store;
import fi.digitalentconsulting.products.repository.ProductRepository;
import fi.digitalentconsulting.products.repository.StoreRepository;

@SpringBootApplication
public class BootProductApplication {
	private static Logger LOGGER = LoggerFactory.getLogger(BootProductApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BootProductApplication.class, args);
	}

	@Bean
	@Profile("dev")
	public ApplicationRunner rundbcode(ProductRepository repo, StoreRepository storeRepository) {
		return args -> {
			Arrays.asList(
					new Product("Product", "Some uninteresting product", Double.valueOf(10)),
					new Product("Thing-ama-ding", "A thingy", 0.3),
					new Product("Hyper", "Hottest hot stuff ever", 234.0),
					new Product("Awesome thing", "Who cares what it is, it's awesome", 1_000_000.0),
					new Product("Mouse", "Mouse with USB connection", 9.99)
					)
				.stream()
				.forEach(p -> {
					Product saved = repo.save(p);
					LOGGER.debug("CREATED: {}", saved);
				});
			
			Store one = new Store("Store One", "Espoo");
			Store large = new Store("Large Store", "Helsinki");
			Store small = new Store("Small Store", "Vantaa");
			List<Product> products = repo.findAll();
			one.addProducts(products.get(0), 12);
			one.addProducts(products.get(1), 4);
			one.addProducts(products.get(2), 7);
			one.addProducts(products.get(3), 9);
			one.addProducts(products.get(4), 123);
			large.addProducts(products.get(0), 1299);
			large.addProducts(products.get(3), 999);
			large.addProducts(products.get(1), 42);
			small.addProducts(products.get(4), 1);
			small.addProducts(products.get(2), 2);
			storeRepository.save(one);
			storeRepository.save(large);
			storeRepository.save(small);
			
			System.out.println("STORE INV: " + one.getInventory());
		};
	}

}
