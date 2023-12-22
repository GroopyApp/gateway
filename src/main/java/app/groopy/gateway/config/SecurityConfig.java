package app.groopy.gateway.config;

import app.groopy.gateway.config.filters.ApiKeyAuthenticationFilter;
import app.groopy.gateway.config.filters.AuthTokenFilter;
import app.groopy.gateway.config.filters.CorsServiceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsUtils;
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
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                )
                .securityContext((securityContext) -> securityContext
                        .securityContextRepository(new RequestAttributeSecurityContextRepository())
                )
                .addFilterBefore(authTokenFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(corsFilter, CorsFilter.class)
                .addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/v1/**"))
                .build();
    }
}
