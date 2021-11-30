package fi.digitalentconsulting.products;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import fi.digitalentconsulting.products.entity.Product;
import fi.digitalentconsulting.products.repository.ProductRepository;
import fi.digitalentconsulting.products.service.DatamuseService;

@DataJpaTest
//@ActiveProfiles("test")
public class ProductRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;
    
    @MockBean
    DatamuseService datamuseService;
    
    @Test
    public void filteringWorks() throws Exception {
    	entityManager.persist(new Product("Product", "Description", 3.14));
    	entityManager.persist(new Product("Product 2", "Description 2", 5.14));
    	assertThat(repository.count()).isEqualTo(2);
    	List<Product> products = repository.findAllByPriceGreaterThan(5.0);
    	assertThat(products.size()).isEqualTo(1);
    	assertThat(products.get(0).getName()).isEqualTo("Product 2");
    }
}
