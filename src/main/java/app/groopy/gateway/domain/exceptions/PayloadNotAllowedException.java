package app.groopy.gateway.domain.exceptions;

import com.google.protobuf.Message;
import lombok.Getter;

public class PayloadNotAllowedException extends Throwable {

    @Getter private Message payload;

    public PayloadNotAllowedException(Message payload) {
        super(String.format("Payload not allowed: %s", payload.toString()));
        this.payload = payload;
    }
}
