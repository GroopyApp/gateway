package app.groopy.gateway.infrastructure.provider;

import app.groopy.protobuf.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * The gRPC implementation for the InternalServiceProvider. It uses the gRPC services to return the expected responses
 */
@Component("gRPCProvider")
public class GrpcProvider implements InternalServiceProvider {

    @GrpcClient("userService")
    UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @GrpcClient("wallService")
    WallServiceGrpc.WallServiceBlockingStub wallServiceStub;

    @GrpcClient("chatService")
    ChatServiceGrpc.ChatServiceBlockingStub chatServiceStub;

    public WallServiceProto.CreateTopicResponse createTopic(WallServiceProto.CreateTopicRequest request) {
        return wallServiceStub.createTopic(request);
    }

    public WallServiceProto.CreateEventResponse createEvent(WallServiceProto.CreateEventRequest request) {
        return wallServiceStub.createEvent(request);
    }

    public WallServiceProto.WallResponse getWall(WallServiceProto.WallRequest request) {
        return wallServiceStub.getWall(request);
    }

    public WallServiceProto.GetTopicResponse getTopic(WallServiceProto.GetTopicRequest request) {
        return wallServiceStub.getTopic(request);
    }

    public WallServiceProto.SubscribeTopicResponse subscribeTopic(WallServiceProto.SubscribeTopicRequest request) {
        return wallServiceStub.subscribeTopic(request);
    }

    public WallServiceProto.SubscribeEventResponse subscribeEvent(WallServiceProto.SubscribeEventRequest request) {
        return wallServiceStub.subscribeEvent(request);
    }

    public UserServiceProto.SignUpResponse signUp(UserServiceProto.SignUpRequest request) {
        return userServiceStub.signUp(request);
    }

    public UserServiceProto.SignInResponse signIn(UserServiceProto.SignInRequest request) {
        return userServiceStub.signIn(request);
    }

    @Override
    public ChatServiceProto.ChatDetailsResponse getChats(ChatServiceProto.ChatDetailsRequest request) {
        return chatServiceStub.getDetails(request);
    }

    @Override
    public ChatServiceProto.CreateChatRoomResponse createChatRoom(ChatServiceProto.CreateChatRoomRequest request) {
        return chatServiceStub.createChannel(request);
    }

    @Override
    public ChatServiceProto.StatusResponse fireMessage(ChatServiceProto.ChatMessageRequest request) {
        return chatServiceStub.sendMessage(request);
    }
}
