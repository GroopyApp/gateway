package app.groopy.gateway.infrastructure.provider;

import app.groopy.protobuf.RoomServiceGrpc;
import app.groopy.protobuf.RoomServiceProto;
import app.groopy.protobuf.UserServiceGrpc;
import app.groopy.protobuf.UserServiceProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * The gRPC implementation for the InternalServiceProvider. It uses the gRPC services to return the expected responses
 */
@Component("gRPCProvider")
public class GrpcProvider implements InternalServiceProvider {

    @GrpcClient("userService")
    UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @GrpcClient("roomService")
    RoomServiceGrpc.RoomServiceBlockingStub roomServiceStub;

    public RoomServiceProto.CreateRoomResponse createRoom(RoomServiceProto.CreateRoomRequest request) {
        return roomServiceStub.createRoom(request);
    }

    public RoomServiceProto.SubscribeRoomResponse subscribeRoom(RoomServiceProto.SubscribeRoomRequest request) {
        return roomServiceStub.subscribe(request);
    }

    public RoomServiceProto.ListRoomResponse searchRoom(RoomServiceProto.ListRoomRequest request) {
        return roomServiceStub.searchRoom(request);
    }

    public RoomServiceProto.ListRoomResponse getUserRooms(RoomServiceProto.ListRoomRequest request) {
        return roomServiceStub.listRoom(request);
    }

    public UserServiceProto.SignUpResponse signUp(UserServiceProto.SignUpRequest request) {
        return userServiceStub.signUp(request);
    }

    public UserServiceProto.SignInResponse signIn(UserServiceProto.SignInRequest request) {
        return userServiceStub.signIn(request);
    }
}
