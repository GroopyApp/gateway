package app.groopy.gateway.application;

import app.groopy.gateway.application.validators.GatewayValidator;
import app.groopy.gateway.domain.exceptions.ExceptionResolver;
import app.groopy.gateway.domain.exceptions.PayloadNotAllowedException;
import app.groopy.gateway.infrastructure.InfrastructureService;
import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;
import app.groopy.protobuf.RoomServiceProto;
import app.groopy.protobuf.UserServiceProto;
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
    public Message get(Message protoRequest) {
        validator.validate(protoRequest);
        try {
            if (protoRequest instanceof RoomServiceProto.CreateRoomRequest) {
                return infrastructureService.createRoom((RoomServiceProto.CreateRoomRequest) protoRequest);
            }
            else if (protoRequest instanceof RoomServiceProto.SubscribeRoomRequest) {
                return infrastructureService.subscribeToRoom((RoomServiceProto.SubscribeRoomRequest) protoRequest);
            }
            else if (protoRequest instanceof RoomServiceProto.ListRoomRequest) {
                return infrastructureService.listRoom((RoomServiceProto.ListRoomRequest) protoRequest);
            }
            else if (protoRequest instanceof UserServiceProto.SignUpRequest) {
                return infrastructureService.signUp((UserServiceProto.SignUpRequest) protoRequest);
            }
            else if (protoRequest instanceof UserServiceProto.SignInRequest) {
                return infrastructureService.signIn((UserServiceProto.SignInRequest) protoRequest);
            }
            else {
                throw new PayloadNotAllowedException(protoRequest);
            }

        } catch (InfrastructureException ex) {
            LOGGER.error(String.format("an error occurred trying to call internal service %s", ex.getServiceName()), ex);
            throw ExceptionResolver.resolve(ex);
        }
    }

    @SneakyThrows
    public Message getUserRooms(String userId) {
        try {
            return infrastructureService.getUserRooms(userId);
        } catch (InfrastructureException e) {
            throw ExceptionResolver.resolve(e);
        }
    }

    @SneakyThrows
    public void deleteAllUsers() {
        try {
            infrastructureService.deleteAllUsers();
        } catch (InfrastructureException e) {
            throw ExceptionResolver.resolve(e);
        }
    }
}
