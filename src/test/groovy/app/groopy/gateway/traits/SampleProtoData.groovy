package app.groopy.gateway.traits

import app.groopy.protobuf.UserServiceProto
import app.groopy.protobuf.RoomServiceProto
import app.groopy.protobuf.GatewayProto
import com.google.protobuf.Any
import com.google.protobuf.LazyStringArrayList
import com.google.protobuf.Message
import com.google.protobuf.Struct

trait SampleProtoData {

    UserServiceProto.SignUpRequest sampleProtoSignUpRequest(Map params = [:]) {
        def defaultParams = [
                username: "test_username",
                email: "test@test.com",
                password: "testPassword",
                photoUrl: "http://www.example.com",
        ]

        def finalParams = defaultParams + params;
        def builder = UserServiceProto.SignUpRequest.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    UserServiceProto.SignUpResponse sampleProtoSignUpResponse(Map params = [:]) {
        def defaultParams = [
                data: sampleProtoUserDetails(),
                token: "1234567890abcdefg",
        ]

        def finalParams = defaultParams + params;
        def builder = UserServiceProto.SignUpResponse.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    UserServiceProto.SignInRequest sampleProtoSignInRequest(Map params = [:]) {
        def defaultParams = [
                email: "test@test.com",
                password: "testPassword",
        ]

        def finalParams = defaultParams + params;
        def builder = UserServiceProto.SignInRequest.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    UserServiceProto.SignInResponse sampleProtoSignInResponse(Map params = [:]) {
        def defaultParams = [
                data: sampleProtoUserDetails(),
                token: "1234567890abcdefg",
        ]

        def finalParams = defaultParams + params;
        def builder = UserServiceProto.SignInResponse.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    UserServiceProto.UserDetails sampleProtoUserDetails(Map params = [:]) {
        def defaultParams = [
                userId: "test_username",
                email: "test@test.com",
        ]

        def finalParams = defaultParams + params;
        def builder = UserServiceProto.UserDetails.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    RoomServiceProto.CreateRoomRequest sampleProtoCreateRoomRequest(Map params = [:]) {
        def defaultParams = [
                roomName: "test-room",
                latitude: "1",
                longitude: "1",
                hashtags_: new LazyStringArrayList(List.of("#test1", "#test2", "#test3", "#test4")),
                languages_: new LazyStringArrayList(List.of("en-GB")),
                userId: "testUser",
        ]

        def finalParams = defaultParams + params;
        def builder = RoomServiceProto.CreateRoomRequest.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    RoomServiceProto.CreateRoomResponse sampleProtoCreateRoomResponse(Map params = [:]) {
        def defaultParams = [
                room: sampleProtoRoom(),
        ]

        def finalParams = defaultParams + params;
        def builder = RoomServiceProto.CreateRoomResponse.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    RoomServiceProto.ListRoomRequest sampleProtoListRoomRequest(Map params = [:]) {
        def defaultParams = [
                latitude: "1",
                longitude: "1",
                hashtags_: new LazyStringArrayList(List.of("#test1", "#test2", "#test3", "#test4")),
                languages_: new LazyStringArrayList(List.of("en-GB")),
                searchRangeInMeters: 1000
        ]

        def finalParams = defaultParams + params;
        def builder = RoomServiceProto.ListRoomRequest.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    RoomServiceProto.ListRoomResponse sampleProtoListRoomResponse(Map params = [:]) {
        def defaultParams = [
                rooms_: List.of(sampleProtoRoom(), sampleProtoRoom(), sampleProtoRoom()),
        ]

        def finalParams = defaultParams + params;
        def builder = RoomServiceProto.ListRoomResponse.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    RoomServiceProto.SubscribeRoomRequest sampleProtoSubscribeRoomRequest(Map params = [:]) {
        def defaultParams = [
                userId: "testUser",
                roomId: "test-room",
        ]

        def finalParams = defaultParams + params;
        def builder = RoomServiceProto.SubscribeRoomRequest.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key as String] = value
        })
        return builder.build()
    }

    RoomServiceProto.SubscribeRoomResponse sampleProtoSubscribeRoomResponse(Map params = [:]) {
        def defaultParams = [
                userId: "testUser",
                room: sampleProtoRoom(),
        ]

        def finalParams = defaultParams + params;
        def builder = RoomServiceProto.SubscribeRoomResponse.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    RoomServiceProto.Room sampleProtoRoom(Map params = [:]) {
        def defaultParams = [
                id: "testId",
                name: "test-room",
                latitude: "1",
                longitude: "1",
                status: "CREATED",
                hashtags_: new LazyStringArrayList(List.of("#test1", "#test2", "#test3", "#test4")),
                languages_: new LazyStringArrayList(List.of("en-GB")),
        ]

        def finalParams = defaultParams + params;
        def builder = RoomServiceProto.Room.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }

    GatewayProto.UserRoomsRequest sampleProtoUserRoomsRequest(Map params = [:]) {
        def defaultParams = [
                userId: "test"
        ]

        def finalParams = defaultParams + params;
        def builder = GatewayProto.UserRoomsRequest.newBuilder();
        finalParams.forEach((key, value) -> {
            builder[key] = value
        })
        return builder.build()
    }
    
    GatewayProto.GatewayRequest sampleGatewayRequest(Message content) {
        var requestBuilder = GatewayProto.GatewayRequest.newBuilder();
        switch (content.getDescriptorForType().getName()) {
            case "SignInRequest" -> requestBuilder.setSignInRequest((UserServiceProto.SignInRequest) content);
            case "SignUpRequest" -> requestBuilder.setSignUpRequest((UserServiceProto.SignUpRequest) content);
            case "CreateRoomRequest" -> requestBuilder.setCreateRoomRequest((RoomServiceProto.CreateRoomRequest) content);
            case "ListRoomRequest" -> requestBuilder.setListRoomRequest((RoomServiceProto.ListRoomRequest) content);
            case "UserRoomsRequest" -> requestBuilder.setUserRoomsRequest((GatewayProto.UserRoomsRequest) content);
            case "SubscribeRoomRequest" -> requestBuilder.setSubscribeRoomRequest((RoomServiceProto.SubscribeRoomRequest) content);
        }
        return requestBuilder.build();
    }

    GatewayProto.GatewayResponse sampleGatewayResponse(Message content) {
        var responseBuilder = GatewayProto.GatewayResponse.newBuilder();
        switch (content.getDescriptorForType().getName()) {
            case "SignInResponse" -> responseBuilder.setSignInResponse((UserServiceProto.SignInResponse) content);
            case "SignUpResponse" -> responseBuilder.setSignUpResponse((UserServiceProto.SignUpResponse) content);
            case "CreateRoomResponse" -> responseBuilder.setCreateRoomResponse((RoomServiceProto.CreateRoomResponse) content);
            case "ListRoomResponse" -> responseBuilder.setListRoomResponse((RoomServiceProto.ListRoomResponse) content);
            case "SubscribeRoomResponse" -> responseBuilder.setSubscribeRoomResponse((RoomServiceProto.SubscribeRoomResponse) content);
        }
        return responseBuilder.build();
    }
}