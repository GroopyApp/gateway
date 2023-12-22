package app.groopy.gateway.domain.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ChatServiceException extends ServiceException {

    public ChatServiceException(String errorMessage, Map<String, String> parameters, HttpStatus status) {
        super(String.format("An error occurred: %s", errorMessage), parameters, status);
    }
}
