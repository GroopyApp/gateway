package app.groopy.gateway.presentation;

import app.groopy.gateway.config.Constants;
import app.groopy.gateway.domain.models.UserContextDto;
import app.groopy.protobuf.*;
import app.groopy.gateway.application.GatewayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static app.groopy.gateway.config.Constants.*;

@RestController
@RequestMapping("/v1")
public class GatewayController {

    private final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);

    private final GatewayService gatewayService;

    @Autowired
    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping(value = AUTH_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> gatewayAuth(@RequestBody GatewayProto.GatewayAuthRequest payload, HttpServletRequest request) {
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

    @PostMapping(value = Constants.WALL_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> gatewayRequest(@RequestBody GatewayProto.GatewayWallRequest payload, HttpServletRequest request) {
        LOGGER.info("Processing message {}", payload);

        var result = gatewayService.process(payload, buildUserContext(request.getSession()));
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

    @PostMapping(value = CHAT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> chatRequest(@RequestBody GatewayProto.GatewayChatRequest payload, HttpServletRequest request) {
        LOGGER.info("Processing message {}", payload);

        var result = gatewayService.process(payload, buildUserContext(request.getSession()));
        var responseBuilder = GatewayProto.GatewayResponse.newBuilder();
        switch (result.getDescriptorForType().getName()) {
            case "ChatDetailsResponse" -> responseBuilder.setChatDetailsResponse((ChatServiceProto.ChatDetailsResponse) result);
            case "ChatHistoryResponse" -> responseBuilder.setChatHistoryResponse((ChatServiceProto.ChatHistoryResponse) result);
            case "StatusResponse" -> responseBuilder.setChatMessageResponse((ChatServiceProto.StatusResponse) result);
        }
        return ResponseEntity.ok(responseBuilder.build());
    }

    @PostMapping(value = THREADS_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> threadsRequest(@RequestBody GatewayProto.GatewayThreadsRequest payload, HttpServletRequest request) {
        LOGGER.info("Processing message {}", payload);

        var result = gatewayService.process(payload, buildUserContext(request.getSession()));
        var responseBuilder = GatewayProto.GatewayResponse.newBuilder();
        switch (result.getDescriptorForType().getName()) {
            case "StatusResponse" -> responseBuilder.setThreadsStatusResponse((ThreadsServiceProto.StatusResponse) result);
        }
        return ResponseEntity.ok(responseBuilder.build());
    }

    private UserContextDto buildUserContext(HttpSession session) {
        UserServiceProto.UserDetails data = (UserServiceProto.UserDetails) session.getAttribute(USER_DETAILS_SESSION);
        return UserContextDto.builder()
                .token((String) session.getAttribute(AUTH_TOKEN_SESSION))
                .userId(data.getUserId())
                .email(data.getEmail())
                .birthDate(data.getBirthDate())
                .name(data.getName())
                .surname(data.getSurname())
                .photoUrl(data.getPhotoUrl())
                .build();
    }
}
