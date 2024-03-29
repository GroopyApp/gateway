package app.groopy.gateway.application;

import app.groopy.gateway.application.validators.GatewayValidator;
import app.groopy.gateway.domain.exceptions.ExceptionResolver;
import app.groopy.gateway.domain.exceptions.PayloadNotAllowedException;
import app.groopy.gateway.domain.models.UserContextDto;
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
    public Message process(GatewayProto.GatewayAuthRequest protoRequest) {
        validator.validate(protoRequest);
        try {
            return switch (protoRequest.getAuthCase()) {
                case SIGNINREQUEST -> infrastructureService.userService().signIn(protoRequest.getSignInRequest());
                case SIGNUPREQUEST -> infrastructureService.userService().signUp(protoRequest.getSignUpRequest());
                default -> throw new PayloadNotAllowedException(protoRequest);
            };
        } catch (InfrastructureException ex) {
            LOGGER.error(String.format("an error occurred trying to call internal service %s", ex.getServiceName()), ex);
            throw ExceptionResolver.resolve(ex);
        }
    }

    @SneakyThrows
    public Message process(GatewayProto.GatewayWallRequest protoRequest, UserContextDto userContext) {
        validator.validate(protoRequest);
        try {
            return switch (protoRequest.getWallCase()) {
                case WALLREQUEST -> infrastructureService.wallService(userContext).getWall(protoRequest.getWallRequest());
                case CREATETOPICREQUEST -> infrastructureService.wallService(userContext).createTopic(protoRequest.getCreateTopicRequest());
                case CREATEEVENTREQUEST -> infrastructureService.wallService(userContext).createEvent(protoRequest.getCreateEventRequest());
                case GETTOPICREQUEST -> infrastructureService.wallService(userContext).getTopic(protoRequest.getGetTopicRequest());
                case SUBSCRIBETOPICREQUEST -> infrastructureService.wallService(userContext).subscribeTopic(protoRequest.getSubscribeTopicRequest());
                case SUBSCRIBEEVENTREQUEST -> infrastructureService.wallService(userContext).subscribeEvent(protoRequest.getSubscribeEventRequest());
                default -> throw new PayloadNotAllowedException(protoRequest);
            };
        } catch (InfrastructureException ex) {
            LOGGER.error(String.format("an error occurred trying to call internal service %s", ex.getServiceName()), ex);
            throw ExceptionResolver.resolve(ex);
        }
    }

    @SneakyThrows
    public Message process(GatewayProto.GatewayChatRequest protoRequest, UserContextDto userContext) {
        validator.validate(protoRequest);
        try {
            return switch (protoRequest.getChatCase()) {
                case CHATDETAILSREQUEST -> infrastructureService.chatService(userContext).getChatDetails(protoRequest.getChatDetailsRequest());
                case CHATMESSAGEREQUEST -> infrastructureService.chatService(userContext).fireMessage(protoRequest.getChatMessageRequest());
                case CHATHISTORYREQUEST -> infrastructureService.chatService(userContext).getHistory(protoRequest.getChatHistoryRequest());
                default -> throw new PayloadNotAllowedException(protoRequest);
            };
        } catch (InfrastructureException ex) {
            LOGGER.error(String.format("an error occurred trying to call internal service %s", ex.getServiceName()), ex);
            throw ExceptionResolver.resolve(ex);
        }
    }

    @SneakyThrows
    public Message process(GatewayProto.GatewayThreadsRequest protoRequest, UserContextDto userContext) {
        validator.validate(protoRequest);
        try {
            return switch (protoRequest.getThreadsCase()) {
                case POSTTHREADREQUEST -> infrastructureService.threadsService(userContext).postThread(protoRequest.getPostThreadRequest());
                default -> throw new PayloadNotAllowedException(protoRequest);
            };
        } catch (InfrastructureException ex) {
            LOGGER.error(String.format("an error occurred trying to call internal service %s", ex.getServiceName()), ex);
            throw ExceptionResolver.resolve(ex);
        }
    }
}
