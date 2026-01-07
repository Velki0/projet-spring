package fr.diginamic.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customSwaggerConfig() {

        return new OpenAPI()
                .info(new Info()
                .title("API Recensement")
                .version("1.0")
                .description("Cette API fournit des données de recensement de population pour la France.")
                .contact(new Contact().name("Martin Michel").email("martin@michel.com")));

    }

}
