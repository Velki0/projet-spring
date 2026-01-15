package fr.diginamic.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {

        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
        );

        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {

        return new BCryptPasswordEncoder();

    }

}
