package app.groopy.gateway.presentation;

import app.groopy.protobuf.ChatServiceProto;
import app.groopy.protobuf.GatewayProto;
import app.groopy.gateway.application.GatewayService;
import app.groopy.protobuf.UserServiceProto;
import app.groopy.protobuf.WallServiceProto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static app.groopy.gateway.config.Constants.AUTH_TOKEN_SESSION;
import static app.groopy.gateway.config.Constants.USER_DETAILS_SESSION;

@RestController
@RequestMapping("/v1")
public class GatewayController {

    private final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);

    private final GatewayService gatewayService;

    @Autowired
    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping(value = "/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> gatewayAuth(@RequestBody GatewayProto.GatewayRequest payload, HttpServletRequest request) {
        LOGGER.info("Processing message {}", payload);

        var result = gatewayService.process(payload);
        var responseBuilder = GatewayProto.GatewayResponse.newBuilder();
        switch (result.getDescriptorForType().getName()) {
            case "SignInResponse" -> {
                UserServiceProto.SignInResponse response = (UserServiceProto.SignInResponse) result;
                responseBuilder.setSignInResponse(response);
                request.getSession().setAttribute(AUTH_TOKEN_SESSION, response.getToken());
                request.getSession().setAttribute(USER_DETAILS_SESSION, response.getData());
            }
            case "SignUpResponse" -> {
                UserServiceProto.SignUpResponse response = (UserServiceProto.SignUpResponse) result;
                responseBuilder.setSignUpResponse(response);
                request.getSession().setAttribute(AUTH_TOKEN_SESSION, response.getToken());
                request.getSession().setAttribute(USER_DETAILS_SESSION, response.getData());
            }
        }
        return ResponseEntity.ok(responseBuilder.build());
    }

    @PostMapping(value = "/request",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> gatewayRequest(@RequestBody GatewayProto.GatewayRequest payload) {
        LOGGER.info("Processing message {}", payload);

        var result = gatewayService.process(payload);
        var responseBuilder = GatewayProto.GatewayResponse.newBuilder();
        switch (result.getDescriptorForType().getName()) {
            case "GetTopicResponse" -> responseBuilder.setGetTopicResponse((WallServiceProto.GetTopicResponse) result);
            case "WallResponse" -> responseBuilder.setWallResponse((WallServiceProto.WallResponse) result);
            case "CreateTopicResponse" -> responseBuilder.setCreateTopicResponse((WallServiceProto.CreateTopicResponse) result);
            case "CreateEventResponse" -> responseBuilder.setCreateEventResponse((WallServiceProto.CreateEventResponse) result);
            case "SubscribeTopicResponse" -> responseBuilder.setSubscribeTopicResponse((WallServiceProto.SubscribeTopicResponse) result);
            case "SubscribeEventResponse" -> responseBuilder.setSubscribeEventResponse((WallServiceProto.SubscribeEventResponse) result);
        }
        return ResponseEntity.ok(responseBuilder.build());
    }

    @PostMapping(value = "/chat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> chatRequest(@RequestBody GatewayProto.GatewayRequest payload) {
        LOGGER.info("Processing message {}", payload);

        var result = gatewayService.process(payload);
        var responseBuilder = GatewayProto.GatewayResponse.newBuilder();
        switch (result.getDescriptorForType().getName()) {
            case "ChatDetailsResponse" -> responseBuilder.setChatDetailsResponse((ChatServiceProto.ChatDetailsResponse) result);
            case "CreateChatRoomResponse" -> responseBuilder.setCreateChatRoomResponse((ChatServiceProto.CreateChatRoomResponse) result);
            case "StatusResponse" -> responseBuilder.setChatMessageResponse((ChatServiceProto.StatusResponse) result);
        }
        return ResponseEntity.ok(responseBuilder.build());
    }
}
