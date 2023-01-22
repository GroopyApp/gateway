package app.groopy.gateway.infrastructure;

import app.groopy.gateway.domain.models.GroopyService;
import app.groopy.gateway.infrastructure.exceptions.InfrastructureException;
import app.groopy.gateway.infrastructure.repository.RoomServiceRepository;
import app.groopy.gateway.infrastructure.repository.UserServiceRepository;
import app.groopy.protobuf.RoomServiceProto;
import app.groopy.protobuf.UserServiceProto;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class InfrastructureService {

    private final Logger LOGGER = LoggerFactory.getLogger(InfrastructureService.class);

    private final RoomServiceRepository roomServiceRepository;
    private final UserServiceRepository userServiceRepository;

    @Autowired
    public InfrastructureService(@Value("${room-service.host}") String roomServiceHost,
                                 @Value("${user-service.host}") String userServiceHost) {

        Retrofit roomServiceRetrofit = new Retrofit.Builder()
                .baseUrl(roomServiceHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Retrofit userServiceRetrofit = new Retrofit.Builder()
                .baseUrl(userServiceHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.roomServiceRepository = roomServiceRetrofit.create(RoomServiceRepository.class);
        this.userServiceRepository = userServiceRetrofit.create(UserServiceRepository.class);
    }

    public Message createRoom(RoomServiceProto.CreateRoomRequest req) throws InfrastructureException {
        try {
            Response<Message> response =  roomServiceRepository.createRoom(req).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new InfrastructureException(GroopyService.ROOM_SERVICE, response.errorBody().string());
            }
        } catch (IOException e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }    }

    public Message subscribeToRoom(RoomServiceProto.SubscribeRoomRequest req) throws InfrastructureException {
        try {
            Response<Message> response =  roomServiceRepository.subscribe(req).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new InfrastructureException(GroopyService.ROOM_SERVICE, response.errorBody().string());
            }
        } catch (IOException e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }    }

    public Message listRoom(RoomServiceProto.ListRoomRequest req) throws InfrastructureException {
        try {
            Response<Message> response =  roomServiceRepository.listRoom(req).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new InfrastructureException(GroopyService.ROOM_SERVICE, response.errorBody().string());
            }
        } catch (IOException e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message getUserRooms(String userId) throws InfrastructureException {
        try {
            Response<Message> response =  roomServiceRepository.getUserRooms(userId).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new InfrastructureException(GroopyService.ROOM_SERVICE, response.errorBody().string());
            }
        } catch (IOException e) {
            throw new InfrastructureException(GroopyService.ROOM_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message signUp(UserServiceProto.SignUpRequest req) throws InfrastructureException {
        try {
            Response<Message> response =  userServiceRepository.signUp(req).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new InfrastructureException(GroopyService.USER_SERVICE, response.errorBody().string());
            }
        } catch (IOException e) {
            throw new InfrastructureException(GroopyService.USER_SERVICE, e.getLocalizedMessage());
        }
    }

    public Message signIn(UserServiceProto.SignInRequest req) throws InfrastructureException {
        try {
            Response<Message> response =  userServiceRepository.signIn(req).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new InfrastructureException(GroopyService.USER_SERVICE, response.errorBody().string());
            }
        } catch (IOException e) {
            throw new InfrastructureException(GroopyService.USER_SERVICE, e.getLocalizedMessage());
        }
    }

    public void deleteAllUsers() throws InfrastructureException {
        try {
            Response response =  userServiceRepository.deleteAllUsers().execute();
            if (response.isSuccessful()) {
                LOGGER.info("Users deleted from firebase DB!");
            } else {
                throw new InfrastructureException(GroopyService.USER_SERVICE, response.errorBody().string());
            }
        } catch (IOException e) {
            throw new InfrastructureException(GroopyService.USER_SERVICE, e.getLocalizedMessage());
        }
    }
}
