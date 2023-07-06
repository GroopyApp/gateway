package app.groopy.gateway.config;

import app.groopy.gateway.config.filters.ApiKeyAuthenticationFilter;
import app.groopy.gateway.config.filters.AuthTokenFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth").permitAll()
                        .requestMatchers("/v1/request").permitAll() //TODO change this to authenticated
                        .anyRequest().authenticated()
                )
                .addFilterBefore(corsFilter, Filter.class)
                .addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(authTokenFilter, BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/v1/request"))
                .build();
    }
}
