package no.ratneck.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/api/concerts/**", "/api/merch/**").permitAll()
                        .anyRequest().authenticated()).formLogin(form -> form.successHandler(
                                ((request, response, authentication) -> response.setStatus(200)))
                        .failureHandler((request, response, exception) -> response.setStatus(401)))
                        .exceptionHandling(ex -> ex.authenticationEntryPoint(
                                ((request, response, authException) -> response.setStatus(401))

                        )).logout(logout -> logout.logoutSuccessHandler((
                                request, response, authentication) -> response.setStatus(204)));

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;

    }
}
