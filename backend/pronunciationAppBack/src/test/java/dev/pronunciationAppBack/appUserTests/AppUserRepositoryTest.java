package dev.pronunciationAppBack.appUserTests;

import dev.pronunciationAppBack.model.AppUser;
import dev.pronunciationAppBack.repository.AppUserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void shouldCreateAppUser() {
        // given
        var appUser = new AppUser("1", "Alice", "alice@example.com", "password", 25, LocalDate.now());
        // when
        appUserRepository.save(appUser);
        // then
        Optional<AppUser> createdUser = appUserRepository.findById("1");
        assertThat(createdUser.isPresent()).isTrue();
        assertThat(createdUser.get().getName()).isEqualTo("Alice");
    }

    @Test
    public void shouldDeleteAppUser() {
        // given
        var appUser = new AppUser("1", "Alice", "alice@example.com", "password", 25, LocalDate.now());
        appUserRepository.save(appUser);
        // when
        appUserRepository.deleteById("1");
        // then
        Optional<AppUser> deletedUser = appUserRepository.findById("1");
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void shouldUpdateAppUser() {
        // given
        var appUser = new AppUser("1", "Alice", "alice@example.com", "password", 25, LocalDate.now());
        appUserRepository.save(appUser);
        // when
        String newEmail = "AliceNewEmail@example.com";
        appUser.setEmail(newEmail);
        appUserRepository.save(appUser);
        // then
        Optional<AppUser> updatedUser = appUserRepository.findById("1");
        assertThat(updatedUser).isNotEmpty();
        assertThat(updatedUser.get().getEmail()).isEqualTo(newEmail);
    }

    @Test
    void shouldFindAppUserByEmail() {
        // given
        var appUser = new AppUser("2", "John", "John@example.com", "password", 25, LocalDate.now());
        appUserRepository.save(appUser);
        // when
        Optional<AppUser> foundUser = appUserRepository.findByEmail("John@example.com");
        // then
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getEmail()).isEqualTo("John@example.com");
    }
}
