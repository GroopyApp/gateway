package app.groopy.gateway.infrastructure.provider;

import app.groopy.protobuf.RoomServiceProto;
import app.groopy.protobuf.UserServiceProto;

/**
 * API interface for defining contract and functionalities that can be called from gateway to the internal services.
 * The interface is not bound with the chosen protocol. It can be REST, SOAP, gRPC or whatever
 */
public interface InternalServiceProvider {

    RoomServiceProto.CreateRoomResponse createRoom(RoomServiceProto.CreateRoomRequest request);

    RoomServiceProto.SubscribeRoomResponse subscribeRoom(RoomServiceProto.SubscribeRoomRequest request);

    RoomServiceProto.ListRoomResponse searchRoom(RoomServiceProto.ListRoomRequest request);

    RoomServiceProto.ListRoomResponse getUserRooms(RoomServiceProto.ListRoomRequest request);

    UserServiceProto.SignUpResponse signUp(UserServiceProto.SignUpRequest request);

    UserServiceProto.SignInResponse signIn(UserServiceProto.SignInRequest request);
}
