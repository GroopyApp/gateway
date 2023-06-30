package app.groopy.gateway.config.filters;

import io.micrometer.common.util.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;

public class RequestHeaderAuthenticationProvider implements AuthenticationProvider {

    private String authSecret;

    public RequestHeaderAuthenticationProvider(String authSecret) {
        super();
        this.authSecret = authSecret;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authSecretKey = String.valueOf(authentication.getPrincipal());

        if(StringUtils.isBlank(authSecretKey) || !authSecretKey.equals(authSecret)) {
            throw new BadCredentialsException("Bad Request Header Credentials");
        }
        return new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), null, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}