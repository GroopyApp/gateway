package app.groopy.gateway.infrastructure.repository;

import app.groopy.protobuf.RoomServiceProto;
import com.google.protobuf.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RoomServiceRepository {

    String CREATE_ROOM_ENDPOINT = "/create";
    String SUBSCRIBE_ENDPOINT = "/subscribe";
    String LIST_ROOM_ENDPOINT = "/search";
    String GET_USER_ROOMS_ENDPOINT = "/list/{userId}";

    @POST(CREATE_ROOM_ENDPOINT)
    Call<Message> createRoom(RoomServiceProto.CreateRoomRequest req);

    @POST(SUBSCRIBE_ENDPOINT)
    Call<Message> subscribe(RoomServiceProto.SubscribeRoomRequest req);

    @POST(LIST_ROOM_ENDPOINT)
    Call<Message> listRoom(RoomServiceProto.ListRoomRequest req);

    @GET(GET_USER_ROOMS_ENDPOINT)
    Call<Message> getUserRooms(String userId);
}
