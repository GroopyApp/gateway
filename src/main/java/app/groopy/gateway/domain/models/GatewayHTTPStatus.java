package app.groopy.gateway.domain.models;

public enum GatewayHTTPStatus {
    NOT_FOUND(404),
    FORBIDDEN(403),
    USER_SERVICE_ERROR(512),
    ROOM_SERVICE_ERROR(513);

    public int statusCode;

    GatewayHTTPStatus(int statusCode) {
        this.statusCode = statusCode;
    }
}
