package app.groopy.gateway.domain.models;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class UserContextDto {

    String userId;
    String token;
    String name;
    String surname;
    String email;
    String photoUrl;
    String birthDate;
    List<String> subscribedTopics;
    List<String> subscribedEvents;
}
