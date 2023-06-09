package app.groopy.gateway.infrastructure.provider;

import app.groopy.protobuf.WallServiceProto;
import app.groopy.protobuf.UserServiceProto;

/**
 * API interface for defining contract and functionalities that can be called from gateway to the internal services.
 * The interface is not bound with the chosen protocol. It can be REST, SOAP, gRPC or whatever
 */
public interface InternalServiceProvider {

    WallServiceProto.CreateTopicResponse createTopic(WallServiceProto.CreateTopicRequest request);

    WallServiceProto.CreateEventResponse createEvent(WallServiceProto.CreateEventRequest request);

    WallServiceProto.GetWallResponse getWall(WallServiceProto.GetWallRequest request);

    WallServiceProto.GetTopicResponse getTopic(WallServiceProto.GetTopicRequest request);

    WallServiceProto.SubscribeTopicResponse subscribeTopic(WallServiceProto.SubscribeTopicRequest request);

    WallServiceProto.SubscribeEventResponse subscribeEvent(WallServiceProto.SubscribeEventRequest request);

    UserServiceProto.SignUpResponse signUp(UserServiceProto.SignUpRequest request);

    UserServiceProto.SignInResponse signIn(UserServiceProto.SignInRequest request);
}
