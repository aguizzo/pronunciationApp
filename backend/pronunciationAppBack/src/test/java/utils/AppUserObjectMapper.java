package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class AppUserObjectMapper {

    public static ObjectMapper getAppUserObjectMapper() {
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
}
