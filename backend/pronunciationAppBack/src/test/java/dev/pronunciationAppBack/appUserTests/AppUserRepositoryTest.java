package dev.pronunciationAppBack.appUserTests;

import dev.pronunciationAppBack.model.AppUser;
import dev.pronunciationAppBack.repository.AppUserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDate;

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
        var createdUser = appUserRepository.save(appUser);
        // then
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("Alice");
    }

    @Test void shouldFindAppUserByEmail() {
        // given
        var appUser = new AppUser("2", "John", "John@example.com", "password", 25, LocalDate.now());
        appUserRepository.save(appUser);
        // when
        AppUser foundUser = appUserRepository.findByEmail("John@example.com").orElse(null);   ;
        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("John@example.com");
    }
}
