package app.groopy.gateway.config;

import app.groopy.protobuf.GatewayProto;
import app.groopy.protobuf.RoomServiceProto;
import app.groopy.protobuf.UserServiceProto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    ProtobufJsonFormatHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufJsonFormatHttpMessageConverter();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(protobufHttpMessageConverter());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        JsonFormat.TypeRegistry typeRegistry = JsonFormat.TypeRegistry.newBuilder()
                .add(GatewayProto.GatewayRequest.getDescriptor())
                .add(GatewayProto.GatewayResponse.getDescriptor())
                .add(GatewayProto.GatewayErrorResponse.getDescriptor())
                .add(GatewayProto.UserRoomsRequest.getDescriptor())
                .add(RoomServiceProto.CreateRoomRequest.getDescriptor())
                .add(RoomServiceProto.ListRoomRequest.getDescriptor())
                .add(RoomServiceProto.SubscribeRoomRequest.getDescriptor())
                .add(UserServiceProto.SignUpRequest.getDescriptor())
                .add(UserServiceProto.SignInRequest.getDescriptor())
                .add(Any.getDescriptor())
                .build();

        JsonFormat.Printer printer = JsonFormat.printer().usingTypeRegistry(typeRegistry)
                .includingDefaultValueFields();
        return o -> o.serializerByType(Message.class, new JsonSerializer<Message>() {
            @Override
            public void serialize(Message message, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeRawValue(printer.print(message));
            }
        });
    }
}
