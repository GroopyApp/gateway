package app.groopy.gateway.infrastructure;

import app.groopy.gateway.domain.models.GroopyService;
import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;
import app.groopy.gateway.infrastructure.provider.InternalServiceProvider;
import app.groopy.protobuf.*;
import com.google.protobuf.Message;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfrastructureService {

    private final Logger LOGGER = LoggerFactory.getLogger(InfrastructureService.class);

    @Autowired
    private InternalServiceProvider internalServiceProvider;

    public class WallService {
        public Message createTopic(WallServiceProto.CreateTopicRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending CreateTopicRequest message to wall-service {}", req.toString());
                return internalServiceProvider.createTopic(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call wall-service with: {}", req);
                throw new InfrastructureException(GroopyService.WALL_SERVICE, e);
            }
        }

        public Message createEvent(WallServiceProto.CreateEventRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending CreateEventRequest message to wall-service {}", req.toString());
                return internalServiceProvider.createEvent(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call wall-service with: {}", req);
                throw new InfrastructureException(GroopyService.WALL_SERVICE, e);
            }
        }

        public Message getWall(WallServiceProto.WallRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending WallRequest message to wall-service {}", req.toString());
                return internalServiceProvider.getWall(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call wall-service with: {}", req);
                throw new InfrastructureException(GroopyService.WALL_SERVICE, e);
            }
        }

        public Message getTopic(WallServiceProto.GetTopicRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending GetTopicRequest message wall-service {}", req.toString());
                return internalServiceProvider.getTopic(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call wall-service with: {}", req);
                throw new InfrastructureException(GroopyService.WALL_SERVICE, e);
            }
        }

        public Message subscribeTopic(WallServiceProto.SubscribeTopicRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending SubscribeTopicRequest message to wall-service");
                return internalServiceProvider.subscribeTopic(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call wall-service");
                throw new InfrastructureException(GroopyService.WALL_SERVICE, e);
            }
        }

        public Message subscribeEvent(WallServiceProto.SubscribeEventRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending SubscribeEventRequest message to wall-service");
                return internalServiceProvider.subscribeEvent(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call wall-service");
                throw new InfrastructureException(GroopyService.WALL_SERVICE, e);
            }
        }
    }

    public class UserService {
        public Message signUp(UserServiceProto.SignUpRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending SignUpRequest message to user-service");
                return internalServiceProvider.signUp(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call user-service");
                throw new InfrastructureException(GroopyService.USER_SERVICE, e);
            }
        }

        public Message signIn(UserServiceProto.SignInRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending SignUpRequest message to user-service");
                return internalServiceProvider.signIn(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call user-service");
                throw new InfrastructureException(GroopyService.USER_SERVICE, e);
            }
        }
    }

    public class ChatService {
        public Message getChatDetails(ChatServiceProto.ChatDetailsRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending ChatDetailsRequest message to chat-service");
                return internalServiceProvider.getChats(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call chat-service");
                throw new InfrastructureException(GroopyService.CHAT_SERVICE, e);
            }
        }

        public Message createChatRoom(ChatServiceProto.CreateChatRoomRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending CreateChatRoomRequest message to chat-service");
                return internalServiceProvider.createChatRoom(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call chat-service");
                throw new InfrastructureException(GroopyService.CHAT_SERVICE, e);
            }
        }

        public Message fireMessage(ChatServiceProto.ChatMessageRequest req) throws InfrastructureException {
            try {
                LOGGER.info("sending ChatMessageRequest message to chat-service");
                return internalServiceProvider.fireMessage(req);
            } catch (StatusRuntimeException e) {
                LOGGER.error("An error occurred trying to call chat-service");
                throw new InfrastructureException(GroopyService.CHAT_SERVICE, e);
            }
        }
    }

    public WallService wallService() {
        return new WallService();
    }

    public UserService userService() {
        return new UserService();
    }

    public ChatService chatService() {
        return new ChatService();
    }
}
