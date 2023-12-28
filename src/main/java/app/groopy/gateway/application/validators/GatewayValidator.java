package app.groopy.gateway.application.validators;

import app.groopy.protobuf.GatewayProto;
import org.springframework.stereotype.Component;

@Component
public class GatewayValidator {
    public void validate(GatewayProto.GatewayAuthRequest request) {
        //TODO implement this
    }

    public void validate(GatewayProto.GatewayWallRequest request) {
        //TODO implement this
    }

    public void validate(GatewayProto.GatewayChatRequest request) {
        //TODO implement this
    }
}
