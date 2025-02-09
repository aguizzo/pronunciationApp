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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import static utils.AppUserObjectMapper.getAppUserObjectMapper;

@WebMvcTest(AppUserController.class)
public class AppUserControllerUnitTest {
    public static final String ID = "1";
    public static final String EMAIL = "alice@example.com";
    public static final String NON_EXISTING_ID = "9999";
    public static final String UPDATED_NAME = "Updated Name";
    public static final String UPDATED_EMAIL = "updated@example.com";
    public static final String UPDATED_PASSWORD = "newpassword";

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
}
