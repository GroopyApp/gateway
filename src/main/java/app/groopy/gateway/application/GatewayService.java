package app.groopy.gateway.application;

import app.groopy.gateway.application.validators.GatewayValidator;
import app.groopy.gateway.domain.exceptions.ExceptionResolver;
import app.groopy.gateway.domain.exceptions.PayloadNotAllowedException;
import app.groopy.gateway.infrastructure.InfrastructureService;
import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;
import app.groopy.protobuf.GatewayProto;
import com.google.protobuf.Message;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    private final Logger LOGGER = LoggerFactory.getLogger(GatewayService.class);

    private final GatewayValidator validator;
    private final InfrastructureService infrastructureService;

    @Autowired
    public GatewayService(GatewayValidator validator, InfrastructureService infrastructureService) {
        this.validator = validator;
        this.infrastructureService = infrastructureService;
    }

    @SneakyThrows
    public Message get(GatewayProto.GatewayRequest protoRequest) {
        validator.validate(protoRequest);
        try {
            return switch (protoRequest.getRequestCase()) {
                case SIGNINREQUEST -> infrastructureService.signIn(protoRequest.getSignInRequest());
                case SIGNUPREQUEST -> infrastructureService.signUp(protoRequest.getSignUpRequest());
                case LISTROOMREQUEST -> infrastructureService.searchRooms(protoRequest.getListRoomRequest());
                case CREATEROOMREQUEST -> infrastructureService.createRoom(protoRequest.getCreateRoomRequest());
                case SUBSCRIBEROOMREQUEST -> infrastructureService.subscribeToRoom(protoRequest.getSubscribeRoomRequest());
                case USERROOMSREQUEST -> infrastructureService.listRoom(protoRequest.getUserRoomsRequest());
                default -> throw new PayloadNotAllowedException(protoRequest);
            };
        } catch (InfrastructureException ex) {
            LOGGER.error(String.format("an error occurred trying to call internal service %s", ex.getServiceName()), ex);
            throw ExceptionResolver.resolve(ex);
        }
    }
}
