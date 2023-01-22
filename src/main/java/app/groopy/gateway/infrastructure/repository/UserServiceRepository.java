package app.groopy.gateway.infrastructure.repository;

import app.groopy.protobuf.UserServiceProto;
import app.groopy.providers.firebase.models.FirebaseSignInRequest;
import app.groopy.providers.firebase.models.FirebaseSignInResponse;
import app.groopy.providers.firebase.models.FirebaseSignUpRequest;
import app.groopy.providers.firebase.models.FirebaseSignUpResponse;
import com.google.protobuf.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.concurrent.Executor;

public interface UserServiceRepository {

    String SIGN_IN_ENDPOINT = "/signIn";
    String SIGN_UP_ENDPOINT = "/signUp";
    String DELETE_USERS_ENDPOINT = "/deleteAll";

    @POST(SIGN_IN_ENDPOINT)
    Call<Message> signIn(@Body UserServiceProto.SignInRequest request);
    @POST(SIGN_UP_ENDPOINT)
    Call<Message> signUp(@Body UserServiceProto.SignUpRequest request);

    @GET(DELETE_USERS_ENDPOINT)
    Call<?> deleteAllUsers();
}
