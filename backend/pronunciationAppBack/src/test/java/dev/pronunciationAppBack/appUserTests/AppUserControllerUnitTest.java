package dev.pronunciationAppBack.appUserTests;

import dev.pronunciationAppBack.controller.AppUserController;
import dev.pronunciationAppBack.model.AppUser;
import dev.pronunciationAppBack.service.AppUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static utils.AppUserObjectMapper.getAppUserObjectMapper;

@WebMvcTest(AppUserController.class)
public class AppUserControllerUnitTest {
    public static final String ID = "1";
    public static final String EMAIL = "alice@example.com";
    public static final String NON_EXISTING_ID = "9999";

    public static final String API_URL = "/api/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserServiceMock;

    private AppUser testAppUser;

    private JacksonTester<AppUser> jsonAppUser;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, getAppUserObjectMapper());
        testAppUser = new AppUser(ID, "Alice", EMAIL, "password", 25, LocalDate.now());
    }

    @Test
    public void shouldReturnAllAppUsersAndOkStatus() throws Exception {
        List<AppUser> appUsers = List.of(testAppUser);
        when(appUserServiceMock.getAllUsers()).thenReturn(appUsers);
        when(appUserServiceMock.getAppUserCount()).thenReturn(Long.valueOf(appUsers.size()));

        mockMvc.perform(get(API_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(appUsers.size()))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value(EMAIL))
                .andExpect(header().exists("user-count"))
                .andExpect(header().string("user-count", String.valueOf(appUsers.size())));
    }

    @Test
    public void shouldReturnNotFoundWhenThereAreNoAppUsers() throws Exception {
        List<AppUser> appUsers = Collections.emptyList();
        when(appUserServiceMock.getAllUsers()).thenReturn(appUsers);
        when(appUserServiceMock.getAppUserCount()).thenReturn(Long.valueOf(0));

        mockMvc.perform(get(API_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("user-count"))
                .andExpect(header().string("user-count", "0"));
    }

    @Test
    public void shouldFindAppUserByIdAndReturnAppUserAndOkStatus() throws Exception {
        when(appUserServiceMock.findAppUserById(ID)).thenReturn(Optional.of(testAppUser));

        MockHttpServletResponse response = mockMvc.perform(
                        get(String.format("%s/%s", API_URL, ID))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonAppUser.write(testAppUser).getJson()
        );
    }

    @Test
    public void shouldReturnNotFoundWhenAppUserNotFoundById() throws Exception {
        when(appUserServiceMock.findAppUserById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        MockHttpServletResponse response = mockMvc.perform(
                        get(String.format("%s/%s", API_URL, NON_EXISTING_ID))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void shouldFindAppUserByEmailAndReturnAppUserAndOkStatus() throws Exception {
        when(appUserServiceMock.findAppUserByEmail(EMAIL)).thenReturn(Optional.of(testAppUser));

        MockHttpServletResponse response = mockMvc.perform(
                        get(String.format("%s/findBy?email=%s", API_URL, EMAIL))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonAppUser.write(testAppUser).getJson()
        );
    }

    @Test
    public void shouldReturnNotFoundWhenAppUserNotFoundByEmail() throws Exception {
        when(appUserServiceMock.findAppUserByEmail(EMAIL)).thenReturn(Optional.empty());

        MockHttpServletResponse response = mockMvc.perform(
                        get(String.format("%s/findBy?email=%s", API_URL, EMAIL))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void shouldCreateAppUserAndReturnAppUserAndCreatedStatus() throws Exception {
        when(appUserServiceMock.createAppUser(any(AppUser.class))).thenReturn(Optional.of(testAppUser));

        MockHttpServletResponse response = mockMvc.perform(
                        post(String.format("%s", API_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAppUser.write(testAppUser).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonAppUser.write(testAppUser).getJson()
        );
    }

    @Test
    public void shouldNotCreateAppUserAndReturnBadRequestStatus() throws Exception {
        when(appUserServiceMock.createAppUser(any(AppUser.class))).thenReturn(Optional.empty());

        MockHttpServletResponse response = mockMvc.perform(
                        post(String.format("%s", API_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void shouldUpdateAppUserAndReturnAppUserAndOkStatus() throws Exception {
        // TODO: Create new AppUser details
        // TODO: add when(appUserServiceMock.findAppUserById(ID)).thenReturn(Optional.of(testAppUser));
        // TODO: change to when(appUserServiceMock.updateAppUser(any(AppUser.class))).thenReturn(Optional.of(updatedDetails);
        when(appUserServiceMock.updateAppUser(any(AppUser.class))).thenReturn(Optional.of(testAppUser));

        MockHttpServletResponse response = mockMvc.perform(
                        put(String.format("%s/%s", API_URL, ID))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAppUser.write(testAppUser).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonAppUser.write(testAppUser).getJson()
        );
    }

    @Test
    // TODO: change method name returnNotFoundStatus
    public void shouldNotUpdateAppUserAndReturnBadRequestStatus() throws Exception {
        // TODO: change to when(appUserServiceMock.findAppUserById(ID)).thenReturn(Optional.ofEmpty()););
        when(appUserServiceMock.updateAppUser(any(AppUser.class))).thenReturn(Optional.empty());

        MockHttpServletResponse response = mockMvc.perform(
                        put(String.format("%s/%s", API_URL, NON_EXISTING_ID))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAppUser.write(testAppUser).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void shouldDeleteAppUserAndReturnOkStatus() throws Exception {
        when(appUserServiceMock.deleteAppUser(ID)).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(
                        delete(String.format("%s/%s", API_URL, ID))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("User deleted");
    }

    @Test
    public void shouldNotDeleteAppUserAndReturnNotFoundStatus() throws Exception {
        when(appUserServiceMock.deleteAppUser(NON_EXISTING_ID)).thenReturn(false);

        MockHttpServletResponse response = mockMvc.perform(
                        delete(String.format("%s/%s", API_URL, NON_EXISTING_ID))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("User not found");
    }
}
