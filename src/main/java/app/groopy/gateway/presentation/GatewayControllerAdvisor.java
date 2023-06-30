package app.groopy.gateway.presentation;

import app.groopy.gateway.domain.exceptions.WallServiceException;
import app.groopy.gateway.domain.exceptions.UserServiceException;
import app.groopy.protobuf.GatewayProto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GatewayControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<GatewayProto.GatewayErrorResponse> handle(
            UserServiceException ex, WebRequest request) {

        GatewayProto.GatewayErrorResponse response = GatewayProto.GatewayErrorResponse.newBuilder()
                .setDescription(ex.getLocalizedMessage())
                .putAllParameters(ex.getParameters() != null ? ex.getParameters() : Map.of())
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(WallServiceException.class)
    public ResponseEntity<GatewayProto.GatewayErrorResponse> handle(
            WallServiceException ex, WebRequest request) {

        GatewayProto.GatewayErrorResponse response = GatewayProto.GatewayErrorResponse.newBuilder()
                .setDescription(ex.getLocalizedMessage())
                .putAllParameters(ex.getParameters() != null ? ex.getParameters() : Map.of())
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }
}