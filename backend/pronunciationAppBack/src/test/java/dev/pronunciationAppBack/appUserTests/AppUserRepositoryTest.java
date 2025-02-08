package dev.pronunciationAppBack.appUserTests;

import dev.pronunciationAppBack.model.AppUser;
import dev.pronunciationAppBack.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class AppUserRepositoryTest {

    public static final String ID = "1";
    public static final String EMAIL = "alice@example.com";

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser testAppUser;

    @BeforeEach
    public void setup() {
        testAppUser = new AppUser(ID, "Alice", EMAIL, "password", 25, LocalDate.now());
    }

    @Test
    public void shouldCreateAppUser() {
        // given appUser
        // when
        appUserRepository.save(testAppUser);
        // then
        Optional<AppUser> createdUser = appUserRepository.findById(ID);
        assertThat(createdUser.isPresent()).isTrue();
        assertThat(createdUser.get().getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void shouldDeleteAppUser() {
        // given
        appUserRepository.save(testAppUser);
        // when
        appUserRepository.deleteById(ID);
        // then
        Optional<AppUser> deletedUser = appUserRepository.findById(ID);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void shouldUpdateAppUser() {
        // given
        appUserRepository.save(testAppUser);
        // when
        String newEmail = "AliceNewEmail@example.com";
        testAppUser.setEmail(newEmail);
        appUserRepository.save(testAppUser);
        // then
        Optional<AppUser> updatedUser = appUserRepository.findById(ID);
        assertThat(updatedUser).isNotEmpty();
        assertThat(updatedUser.get().getEmail()).isEqualTo(newEmail);
    }

    @Test
    void shouldFindAppUserByEmail() {
        // given
        appUserRepository.save(testAppUser);
        // when
        Optional<AppUser> foundUser = appUserRepository.findByEmail(EMAIL);
        // then
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getEmail()).isEqualTo(EMAIL);
    }
}
