package dev.pronunciationAppBack.appUserTests;

import dev.pronunciationAppBack.model.AppUser;
import dev.pronunciationAppBack.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import utils.AppUserObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class AppUserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

    public static final String ID = "1";
    public static final String EMAIL = "alice@example.com";
    public static final String NON_EXISTING_ID = "9999";

    public static final String API_URL = "/api/users";

    private AppUser testAppUser;

    @BeforeEach
    void setup() {
        appUserRepository.deleteAll();
        testAppUser = new AppUser(ID, "Alice", EMAIL, "password", 25, LocalDate.now());
    }

    @Test
    public void shouldReturnAllAppUsersAndOkStatus() throws Exception {
        appUserRepository.save(testAppUser);
        List<AppUser> appUsers = appUserRepository.findAll();

        mockMvc.perform(get(API_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(AppUserObjectMapper.serializeList(appUsers)));
    }

    @Test
    public void shouldReturnNotFoundWhenAppUserNotFoundById() throws Exception {
        mockMvc.perform(get(String.format("%s/%s", API_URL, NON_EXISTING_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldFindAppUserByIdAndReturnAppUserAndOkStatus() throws Exception {
        appUserRepository.save(testAppUser);

        mockMvc.perform(get(String.format("%s/%s", API_URL, ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(AppUserObjectMapper.serialize(testAppUser)));
    }
}
