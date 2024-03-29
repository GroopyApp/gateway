package app.groopy.gateway.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class UserServiceException extends ServiceException {

    public UserServiceException(String errorMessage, Map<String, String> parameters, HttpStatus status) {
        super(String.format("An error occurred: %s", errorMessage), parameters, status);
    }
}

