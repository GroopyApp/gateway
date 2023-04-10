package app.groopy.gateway.infrastructure;

import app.groopy.gateway.domain.models.GroopyService;
import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;
import app.groopy.gateway.infrastructure.provider.InternalServiceProvider;
import app.groopy.protobuf.*;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfrastructureService {

    private final Logger LOGGER = LoggerFactory.getLogger(InfrastructureService.class);

    @Autowired
    private InternalServiceProvider internalServiceProvider;

    // RoomService calls
    public Message createRoom(RoomServiceProto.CreateRoomRequest req) throws InfrastructureException {
        try {
            LOGGER.info("sending CreateRoom message to room-service {}", req.toString());
            return internalServiceProvider.createRoom(req);
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to call room-service with: {}", req);
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message subscribeToRoom(RoomServiceProto.SubscribeRoomRequest req) throws InfrastructureException {
        try {
            LOGGER.info("sending SubscribeRoom message to room-service {}", req.toString());
            return internalServiceProvider.subscribeRoom(req);
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to call room-service with: {}", req);
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message searchRooms(RoomServiceProto.ListRoomRequest req) throws InfrastructureException {
        try {
            LOGGER.info("sending ListRoomRequest message for search scope to room-service {}", req.toString());
            return internalServiceProvider.searchRoom(req);
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to call room-service with: {}", req);
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message listRoom(GatewayProto.UserRoomsRequest req) throws InfrastructureException {
        try {
            LOGGER.info("sending ListRoomRequest message for user list rooms scope to room-service {}", req.toString());
            return internalServiceProvider.getUserRooms(RoomServiceProto.ListRoomRequest.newBuilder()
                    .setUserId(req.getUserId())
                    .build());
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to call room-service with: {}", req);
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    // UserService calls
    public Message signUp(UserServiceProto.SignUpRequest req) throws InfrastructureException {
        try {
            LOGGER.info("sending SignUpRequest message to user-service");
            return internalServiceProvider.signUp(req);
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to call user-service");
            throw new InfrastructureException(GroopyService.USER_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message signIn(UserServiceProto.SignInRequest req) throws InfrastructureException {
        try {
            LOGGER.info("sending SignUpRequest message to user-service");
            return internalServiceProvider.signIn(req);
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to call user-service");
            throw new InfrastructureException(GroopyService.USER_SERVICE, e.getLocalizedMessage());
        }
    }
}
