package app.groopy.gateway.infrastructure;

import app.groopy.gateway.domain.models.GroopyService;
import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;
import app.groopy.protobuf.RoomServiceGrpc;
import app.groopy.protobuf.RoomServiceProto;
import app.groopy.protobuf.UserServiceGrpc;
import app.groopy.protobuf.UserServiceProto;
import com.google.protobuf.Message;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InfrastructureService {

    private final Logger LOGGER = LoggerFactory.getLogger(InfrastructureService.class);

    @GrpcClient("userService")
    UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @GrpcClient("roomService")
    RoomServiceGrpc.RoomServiceBlockingStub roomServiceStub;

    public Message createRoom(RoomServiceProto.CreateRoomRequest req) throws InfrastructureException {
        try {
            return roomServiceStub.createRoom(req);
        } catch (Exception e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message subscribeToRoom(RoomServiceProto.SubscribeRoomRequest req) throws InfrastructureException {
        try {
            return roomServiceStub.subscribe(req);
        } catch (Exception e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message searchRooms(RoomServiceProto.ListRoomRequest req) throws InfrastructureException {
        try {
            return roomServiceStub.searchRoom(req);
        } catch (Exception e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message getUserRooms(String userId) throws InfrastructureException {
        try {
            return roomServiceStub.listRoom(RoomServiceProto.ListRoomRequest.newBuilder()
                    .setUserId(userId)
                    .build());
        } catch (Exception e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }    }

    public Message signUp(UserServiceProto.SignUpRequest req) throws InfrastructureException {
        try {
            return userServiceStub.signUp(req);
        } catch (Exception e) {
            throw new InfrastructureException(GroopyService.USER_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message signIn(UserServiceProto.SignInRequest req) throws InfrastructureException {
        try {
            return userServiceStub.signIn(req);
        } catch (Exception e) {
            throw new InfrastructureException(GroopyService.USER_SERVICE, e.getLocalizedMessage());
        }
    }
}
