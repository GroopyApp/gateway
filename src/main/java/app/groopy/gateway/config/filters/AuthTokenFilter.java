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

import static app.groopy.gateway.config.Constants.AUTH_TOKEN_SESSION;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final String REQUEST_ENDPOINT = "/request";
    private static final String CHAT_ENDPOINT = "/chat";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //TODO add logic to validate the token
        if (request.getRequestURI().endsWith(REQUEST_ENDPOINT) || request.getRequestURI().endsWith(CHAT_ENDPOINT)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(AUTH_TOKEN_SESSION) == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid auth token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}

