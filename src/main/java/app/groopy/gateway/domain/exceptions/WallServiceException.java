package app.groopy.gateway.domain.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class WallServiceException extends ServiceException {

    public WallServiceException(String errorMessage, Map<String, String> parameters, HttpStatus status) {
        super(String.format("An error occurred: %s", errorMessage), parameters, status);
    }
}
