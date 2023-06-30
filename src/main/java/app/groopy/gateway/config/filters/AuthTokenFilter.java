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
public class AuthTokenFilter extends OncePerRequestFilter {

    @Value("${groopy.app.token.header}")
    private String AUTH_TOKEN_HEADER;

    private static final String REQUEST_ENDPOINT = "/request";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //TODO add logic to validate the token
        if (request.getRequestURI().endsWith(REQUEST_ENDPOINT)) {
            String authToken = request.getHeader(AUTH_TOKEN_HEADER);

            if (authToken == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid auth token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}