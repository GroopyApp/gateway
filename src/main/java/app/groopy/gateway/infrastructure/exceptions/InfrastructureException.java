package app.groopy.gateway.infrastructure.exceptions;

import app.groopy.gateway.domain.models.GroopyService;
import lombok.Getter;

public class InfrastructureException extends Throwable {

    @Getter GroopyService serviceName;

    public InfrastructureException(GroopyService serviceName, String errorMessage) {
        super(String.format("An error occurred invoking internal service %s. exception: %s", serviceName, errorMessage));
        this.serviceName = serviceName;
    }
}
