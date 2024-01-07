package app.groopy.gateway.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import static app.groopy.gateway.config.Constants.*;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //TODO add logic to validate the token
        if (request.getRequestURI().endsWith(WALL_ENDPOINT) ||
                request.getRequestURI().endsWith(CHAT_ENDPOINT) ||
                request.getRequestURI().endsWith(THREADS_ENDPOINT)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(AUTH_TOKEN_SESSION) == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid auth token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}

