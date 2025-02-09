package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import dev.pronunciationAppBack.model.AppUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class AppUserObjectMapper {

    private static final ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter();
        LocalDateDeserializer deserializer = new LocalDateDeserializer(formatter);
        LocalDateSerializer serializer = new LocalDateSerializer(formatter);

        JavaTimeModule timeModule = (JavaTimeModule) new JavaTimeModule()
                .addDeserializer(LocalDate.class, deserializer)
                .addSerializer(LocalDate.class, serializer);

        return new ObjectMapper().registerModule(timeModule);
    }

    public static String serialize(AppUser user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }

    public static String serializeList(List<AppUser> users) throws JsonProcessingException {
        return objectMapper.writeValueAsString(users);
    }

    public static AppUser deserialize(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, AppUser.class);
    }

    public static List<AppUser> deserializeList(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<List<AppUser>>() {});
    }

    // Legacy method, to be removed once that we refactor the Unit tests
    public static ObjectMapper getAppUserObjectMapper() {
        return objectMapper;
    }
}
