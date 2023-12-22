package app.groopy.gateway.domain.exceptions;

import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;

public class ExceptionResolver {

    public static Throwable resolve(InfrastructureException ex) {
       return switch (ex.getServiceName()) {
            case USER_SERVICE -> new UserServiceException(ex.getLocalizedMessage(), ex.getParameters(), ex.getStatus());
            case WALL_SERVICE -> new WallServiceException(ex.getLocalizedMessage(), ex.getParameters(), ex.getStatus());
            case CHAT_SERVICE -> new ChatServiceException(ex.getLocalizedMessage(), ex.getParameters(), ex.getStatus());
       };
    }
}
