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

    private AppUser appUser;

    @BeforeEach
    public void setup() {
        appUser = new AppUser(ID, "Alice", EMAIL, "password", 25, LocalDate.now());
    }

    @Test
    public void shouldCreateAppUser() {
        // given appUser
        // when
        appUserRepository.save(appUser);
        // then
        Optional<AppUser> createdUser = appUserRepository.findById(ID);
        assertThat(createdUser.isPresent()).isTrue();
        assertThat(createdUser.get().getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void shouldDeleteAppUser() {
        // given
        appUserRepository.save(appUser);
        // when
        appUserRepository.deleteById(ID);
        // then
        Optional<AppUser> deletedUser = appUserRepository.findById(ID);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void shouldUpdateAppUser() {
        // given
        appUserRepository.save(appUser);
        // when
        String newEmail = "AliceNewEmail@example.com";
        appUser.setEmail(newEmail);
        appUserRepository.save(appUser);
        // then
        Optional<AppUser> updatedUser = appUserRepository.findById(ID);
        assertThat(updatedUser).isNotEmpty();
        assertThat(updatedUser.get().getEmail()).isEqualTo(newEmail);
    }

    @Test
    void shouldFindAppUserByEmail() {
        // given
        appUserRepository.save(appUser);
        // when
        Optional<AppUser> foundUser = appUserRepository.findByEmail(EMAIL);
        // then
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getEmail()).isEqualTo(EMAIL);
    }
}
