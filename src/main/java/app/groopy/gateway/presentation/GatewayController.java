package app.groopy.gateway.presentation;

import app.groopy.protobuf.GatewayProto;
import app.groopy.gateway.application.GatewayService;
import com.google.protobuf.Any;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/v1")
public class GatewayController {

    private final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);

    private final GatewayService gatewayService;

    @Autowired
    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping(value = "/gateway",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> gateway(@RequestBody Message payload) {
        LOGGER.info("Processing message {}", payload);
        GatewayProto.GatewayResponse response = GatewayProto.GatewayResponse.newBuilder()
                .setResponse(Any.newBuilder().setValue(gatewayService.get(payload).toByteString()).build()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/get/rooms/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GatewayProto.GatewayResponse> getRoomsByUserId(@PathVariable("userId") String userId) {
        GatewayProto.GatewayResponse response = GatewayProto.GatewayResponse.newBuilder()
                .setResponse(Any.newBuilder()
                        .setValue(gatewayService.getUserRooms(userId).toByteString())
                        .build())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "dev/deleteAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAllUsers() {
        gatewayService.deleteAllUsers();
        return ResponseEntity.ok().build();
    }}