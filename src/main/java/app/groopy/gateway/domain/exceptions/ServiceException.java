package app.groopy.gateway.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class ServiceException extends Throwable {

    @Getter
    private Map<String, String> parameters;
    @Getter
    private HttpStatus status;

    public ServiceException(String message, Map<String, String> parameters, HttpStatus status) {
        super(message);
        this.parameters = parameters;
        this.status = status;
    }
}
