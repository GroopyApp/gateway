package app.groopy.gateway.config;

import app.groopy.gateway.config.filters.ApiKeyAuthenticationFilter;
import app.groopy.gateway.config.filters.AuthTokenFilter;
import app.groopy.gateway.config.filters.CorsServiceFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private CorsServiceFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth").permitAll()
                        .requestMatchers("/v1/request").permitAll() //TODO change this to authenticated
                        .anyRequest().authenticated()
                )
                .addFilterBefore(corsFilter, CorsFilter.class)
                .addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(authTokenFilter, BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/v1/request"))
                .build();
    }
}
