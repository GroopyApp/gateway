package app.groopy.gateway.infrastructure.exceptions;

import app.groopy.gateway.domain.models.GroopyService;
import app.groopy.protobuf.WallServiceProto;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class InfrastructureException extends Throwable {

    @Getter GroopyService serviceName;
    @Getter Map<String, String> parameters;
    @Getter HttpStatus status;

    public InfrastructureException(GroopyService serviceName, StatusRuntimeException exception) {
        super(String.format("An error occurred invoking internal service %s. exception: %s", serviceName, exception.getLocalizedMessage()));
        this.serviceName = serviceName;
        Metadata metadata = Status.trailersFromThrowable(exception);
        if (metadata != null) {
            WallServiceProto.ErrorResponse errorResponse = metadata.get(ProtoUtils.keyForProto(WallServiceProto.ErrorResponse.getDefaultInstance()));
            this.parameters = errorResponse != null ? errorResponse.getParametersMap() : null;
        }
        this.status = resolveHttpStatus(exception.getStatus());
    }

    private HttpStatus resolveHttpStatus(Status status) {
        return switch (status.getCode()) {
            case ALREADY_EXISTS -> HttpStatus.METHOD_NOT_ALLOWED;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            case UNKNOWN -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.NOT_IMPLEMENTED;
        };
    }
}
