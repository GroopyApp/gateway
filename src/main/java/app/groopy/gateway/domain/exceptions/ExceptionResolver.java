package app.groopy.gateway.domain.exceptions;

import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;

public class ExceptionResolver {

    public static Throwable resolve(InfrastructureException ex) {
       return switch (ex.getServiceName()) {
            case USER_SERVICE -> new UserServiceException(ex.getLocalizedMessage());
            case ROOM_SERVICE -> new RoomServiceException(ex.getLocalizedMessage());
        };
    }
}
