package fr.diginamic.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")
                                 .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                                 .anyRequest().authenticated()
        );

        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {

        return new BCryptPasswordEncoder();

    }

}
