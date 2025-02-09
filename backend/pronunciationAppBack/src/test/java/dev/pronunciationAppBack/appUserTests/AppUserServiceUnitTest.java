package dev.pronunciationAppBack.appUserTests;

import dev.pronunciationAppBack.model.AppUser;
import dev.pronunciationAppBack.repository.AppUserRepository;
import dev.pronunciationAppBack.service.AppUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceUnitTest {
    public static final String ID = "1";
    public static final String EMAIL = "alice@example.com";
    public static final String NON_EXISTING_ID = "9999";
    public static final String UPDATED_NAME = "Updated Name";
    public static final String UPDATED_EMAIL = "updated@example.com";
    public static final String UPDATED_PASSWORD = "newpassword";

    @Mock
    private AppUserRepository appUserRepositoryMock;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser testAppUser;

    @BeforeEach
    public void setup() {
        testAppUser = new AppUser(ID, "Alice", EMAIL, "password", 25, LocalDate.now());
    }

    @Test
    public void shouldCreateAppUser() {
        when(appUserRepositoryMock.save(any(AppUser.class))).thenReturn(testAppUser);

        Optional<AppUser> createdUser = appUserService.createAppUser(testAppUser);
        assertThat(createdUser).isNotEmpty();
        assertThat(createdUser.get().getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void shouldFindAppUserById() {
        when(appUserRepositoryMock.findById(ID)).thenReturn(Optional.of(testAppUser));

        Optional<AppUser> foundUser = appUserService.findAppUserById(ID);
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnEmptyWhenAppUserNotFoundById() {
        when(appUserRepositoryMock.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<AppUser> foundUser = appUserService.findAppUserById(NON_EXISTING_ID);
        assertThat(foundUser).isEmpty();
    }

    @Test
    void shouldUpdateAppUser() {
        // TODO: Fix this test for updating the age
        AppUser updatedDetails = new AppUser(ID, UPDATED_NAME, UPDATED_EMAIL, UPDATED_PASSWORD, 30, LocalDate.now());

        when(appUserRepositoryMock.findById(ID)).thenReturn(Optional.of(testAppUser));
        when(appUserRepositoryMock.save(any(AppUser.class))).thenReturn(updatedDetails);

        Optional<AppUser> updatedUser = appUserService.updateAppUser(updatedDetails);

        assertThat(updatedUser).isNotEmpty();
        assertThat(updatedUser.get().getName()).isEqualTo(UPDATED_NAME);
        assertThat(updatedUser.get().getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(updatedUser.get().getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    void shouldReturnEmptyWhenUpdatingNonExistentAppUser() {
        AppUser updatedDetails = new AppUser(NON_EXISTING_ID, UPDATED_NAME, UPDATED_EMAIL, UPDATED_PASSWORD, 30, LocalDate.now());

        when(appUserRepositoryMock.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());
        Optional<AppUser> updatedAppUser = appUserService.updateAppUser(updatedDetails);
        assertThat(updatedAppUser).isEmpty();
    }

    @Test
    void shouldDeleteUser() {
        when(appUserRepositoryMock.findById(ID)).thenReturn(Optional.of(testAppUser));
        doNothing().when(appUserRepositoryMock).deleteById(ID);

        boolean isDeleted = appUserService.deleteAppUser(ID);
        assertThat(isDeleted).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentUser() {
        when(appUserRepositoryMock.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        boolean isDeleted = appUserService.deleteAppUser(NON_EXISTING_ID);
        assertThat(isDeleted).isFalse();
    }

    @Test
    public void shouldFindAppUserByEmail() {
        when(appUserRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(testAppUser));

        Optional<AppUser> foundUser = appUserService.findAppUserByEmail(EMAIL);
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getEmail()).isEqualTo(EMAIL);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        when(appUserRepositoryMock.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        Optional<AppUser> foundUser = appUserService.findAppUserByEmail("notfound@example.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    void shouldReturnAllAppUsers() {
        List<AppUser> users = List.of(testAppUser);
        when(appUserRepositoryMock.findAll()).thenReturn(users);

        List<AppUser> result = appUserService.getAllUsers();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getEmail()).isEqualTo(EMAIL);
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersFound() {
        List<AppUser> users = Collections.emptyList();
        when(appUserRepositoryMock.findAll()).thenReturn(users);

        List<AppUser> result = appUserService.getAllUsers();

        assertThat(result).isEmpty();
    }
}
