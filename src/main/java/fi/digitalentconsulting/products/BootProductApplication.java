package fi.digitalentconsulting.products;

import java.util.Arrays;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import fi.digitalentconsulting.products.entity.Product;
import fi.digitalentconsulting.products.repository.ProductRepository;

@SpringBootApplication
public class BootProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootProductApplication.class, args);
	}

	@Bean
	@Profile("dev")
	public ApplicationRunner rundbcode(ProductRepository repo) {
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
					repo.save(p);					
				});
			
			repo.findAll().forEach(prod->{
				System.out.println("CREATED: " + prod);
			});
		};
	}

}
