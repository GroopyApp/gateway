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
    public Message process(GatewayProto.GatewayRequest protoRequest) {
        validator.validate(protoRequest);
        try {
            return switch (protoRequest.getRequestCase()) {
                case SIGNINREQUEST -> infrastructureService.userService().signIn(protoRequest.getSignInRequest());
                case SIGNUPREQUEST -> infrastructureService.userService().signUp(protoRequest.getSignUpRequest());
                case WALLREQUEST -> infrastructureService.wallService().getWall(protoRequest.getWallRequest());
                case CREATETOPICREQUEST -> infrastructureService.wallService().createTopic(protoRequest.getCreateTopicRequest());
                case CREATEEVENTREQUEST -> infrastructureService.wallService().createEvent(protoRequest.getCreateEventRequest());
                case GETTOPICREQUEST -> infrastructureService.wallService().getTopic(protoRequest.getGetTopicRequest());
                case SUBSCRIBETOPICREQUEST -> infrastructureService.wallService().subscribeTopic(protoRequest.getSubscribeTopicRequest());
                case SUBSCRIBEEVENTREQUEST -> infrastructureService.wallService().subscribeEvent(protoRequest.getSubscribeEventRequest());
                case CHATDETAILSREQUEST -> infrastructureService.chatService().getChatDetails(protoRequest.getChatDetailsRequest());
                case CREATECHATROOMREQUEST -> infrastructureService.chatService().createChatRoom(protoRequest.getCreateChatRoomRequest());
                case CHATMESSAGEREQUEST -> infrastructureService.chatService().fireMessage(protoRequest.getChatMessageRequest());
                default -> throw new PayloadNotAllowedException(protoRequest);
            };
        } catch (InfrastructureException ex) {
            LOGGER.error(String.format("an error occurred trying to call internal service %s", ex.getServiceName()), ex);
            throw ExceptionResolver.resolve(ex);
        }
    }
}
