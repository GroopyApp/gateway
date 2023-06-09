package app.groopy.gateway.presentation;

import app.groopy.protobuf.GatewayProto;
import app.groopy.gateway.application.GatewayService;
import app.groopy.protobuf.UserServiceProto;
import app.groopy.protobuf.WallServiceProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class GatewayController {

    private final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);

    private final GatewayService gatewayService;

    @Autowired
    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping(value = "/request",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> gateway(@RequestBody GatewayProto.GatewayRequest payload) {
        LOGGER.info("Processing message {}", payload);

        var result = gatewayService.process(payload);
        var responseBuilder = GatewayProto.GatewayResponse.newBuilder();
        switch (result.getDescriptorForType().getName()) {
            case "SignInResponse" -> responseBuilder.setSignInResponse((UserServiceProto.SignInResponse) result);
            case "SignUpResponse" -> responseBuilder.setSignUpResponse((UserServiceProto.SignUpResponse) result);
            case "GetTopicResponse" -> responseBuilder.setGetTopicResponse((WallServiceProto.GetTopicResponse) result);
            case "GetWallResponse" -> responseBuilder.setGetWallResponse((WallServiceProto.GetWallResponse) result);
            case "CreateTopicResponse" -> responseBuilder.setCreateTopicResponse((WallServiceProto.CreateTopicResponse) result);
            case "CreateEventResponse" -> responseBuilder.setCreateEventResponse((WallServiceProto.CreateEventResponse) result);
            case "SubscribeTopicResponse" -> responseBuilder.setSubscribeTopicResponse((WallServiceProto.SubscribeTopicResponse) result);
            case "SubscribeEventResponse" -> responseBuilder.setSubscribeEventResponse((WallServiceProto.SubscribeEventResponse) result);
        }
        return ResponseEntity.ok(responseBuilder.build());
    }
}