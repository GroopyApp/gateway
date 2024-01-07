package app.groopy.gateway.domain.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ThreadsServiceException extends ServiceException {

    public ThreadsServiceException(String errorMessage, Map<String, String> parameters, HttpStatus status) {
        super(String.format("An error occurred: %s", errorMessage), parameters, status);
    }
}
