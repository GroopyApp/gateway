package app.groopy.gateway.presentation;

import app.groopy.gateway.domain.exceptions.RoomServiceException;
import app.groopy.gateway.domain.exceptions.UserServiceException;
import app.groopy.gateway.domain.models.GatewayHTTPStatus;
import app.groopy.protobuf.GatewayProto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GatewayControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<GatewayProto.GatewayErrorResponse> handle(
            UserServiceException ex, WebRequest request) {

        GatewayProto.GatewayErrorResponse response = GatewayProto.GatewayErrorResponse.newBuilder()
                .setDescription(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(GatewayHTTPStatus.USER_SERVICE_ERROR.statusCode));
    }

    @ExceptionHandler(RoomServiceException.class)
    public ResponseEntity<GatewayProto.GatewayErrorResponse> handle(
            RoomServiceException ex, WebRequest request) {

        GatewayProto.GatewayErrorResponse response = GatewayProto.GatewayErrorResponse.newBuilder()
                .setDescription(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(GatewayHTTPStatus.ROOM_SERVICE_ERROR.statusCode));
    }
}