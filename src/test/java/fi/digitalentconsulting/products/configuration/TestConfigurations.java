package fi.digitalentconsulting.products.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import fi.digitalentconsulting.products.service.DatamuseService;

@Configuration
@Profile("test")
public class TestConfigurations {
    @Bean @Primary
    public DatamuseService datamuseService() {
        return Mockito.mock(DatamuseService.class);
    }    
}
