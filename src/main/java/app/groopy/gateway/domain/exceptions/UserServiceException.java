package app.groopy.gateway.domain.exceptions;

public class UserServiceException extends Throwable {

    public UserServiceException(String errorMessage) {
        super(String.format("An error occurred invoking user-service. exception: %s", errorMessage));
    }
}
