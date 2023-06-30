package app.groopy.gateway.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    @Value("${groopy.app.apiKey.header}")
    private String apiKeyHeader;

    @Value("${groopy.app.apiKey.value}")
    private String apiKeyValue;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader(apiKeyHeader);

        if (apiKey == null || !apiKey.equals(apiKeyValue)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid API key");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
