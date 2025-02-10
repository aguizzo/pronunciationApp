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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    public static final String UPDATED_NAME = "Updated Name";
    public static final String UPDATED_EMAIL = "updated@example.com";
    public static final String UPDATED_PASSWORD = "newpassword";

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
        List<AppUser> appUsers = List.of(testAppUser);

        mockMvc.perform(get(API_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(AppUserObjectMapper.serializeList(appUsers)))
                .andExpect(header().exists("user-count"))
                .andExpect(header().string("user-count", String.valueOf(appUsers.size())));
    }

    @Test
    public void shouldReturnNotFoundWhenThereAreNoAppUsers() throws Exception {
        mockMvc.perform(get(API_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""))
                .andExpect(header().exists("user-count"))
                .andExpect(header().string("user-count", "0"));
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

    @Test
    public void shouldReturnNotFoundWhenAppUserNotFoundById() throws Exception {
        mockMvc.perform(get(String.format("%s/%s", API_URL, NON_EXISTING_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldFindAppUserByEmailAndReturnAppUserAndOkStatus() throws Exception {
        appUserRepository.save(testAppUser);

        mockMvc.perform(get(String.format("%s/findBy?email=%s", API_URL, EMAIL))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(AppUserObjectMapper.serialize(testAppUser)));
    }

    @Test
    public void shouldReturnNotFoundWhenAppUserNotFoundByEmail() throws Exception {
        mockMvc.perform(get(String.format("%s/findBy?email=%s", API_URL, EMAIL))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldCreateAppUserAndReturnAppUserAndCreatedStatus() throws Exception {
        mockMvc.perform(post(String.format("%s", API_URL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AppUserObjectMapper.serialize(testAppUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(AppUserObjectMapper.serialize(testAppUser)));
        // Should I test the database too?
        Optional<AppUser> foundUser = appUserRepository.findById(ID);
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void shouldNotCreateAppUserAndReturnBadRequestStatus() throws Exception {
        mockMvc.perform(post(String.format("%s", API_URL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        // Should I test the database too?
        assertThat(appUserRepository.count()).isEqualTo(0L);
    }

    @Test
    public void shouldUpdateAppUserAndReturnAppUserAndOkStatus() throws Exception {
        appUserRepository.save(testAppUser);
        AppUser updatedDetails = new AppUser(ID, UPDATED_NAME, UPDATED_EMAIL, UPDATED_PASSWORD, 30, LocalDate.now());

        mockMvc.perform(put(String.format("%s/%s", API_URL, ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AppUserObjectMapper.serialize(updatedDetails))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(AppUserObjectMapper.serialize(updatedDetails)));
        // Should I test the database too?
    }

    @Test
    public void shouldNotUpdateAppUserAndReturnNotFoundStatus() throws Exception {
        mockMvc.perform(put(String.format("%s/%s", API_URL, NON_EXISTING_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AppUserObjectMapper.serialize(testAppUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
        // Should I test the database too?
    }

    @Test
    public void shouldDeleteAppUserAndOkStatus() throws Exception {
        appUserRepository.save(testAppUser);

        mockMvc.perform(delete(String.format("%s/%s", API_URL, ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

        assertThat(appUserRepository.count()).isEqualTo(0L);
    }

    @Test
    public void shouldNotDeleteAppUserAndReturnNotFoundStatus() throws Exception {
        mockMvc.perform(delete(String.format("%s/%s", API_URL, NON_EXISTING_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

}
