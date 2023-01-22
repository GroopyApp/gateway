package app.groopy.gateway.domain.exceptions;

public class RoomServiceException extends Throwable {


    public RoomServiceException(String errorMessage) {
        super(String.format("An error occurred invoking room-service. exception: %s", errorMessage));

    }
}
