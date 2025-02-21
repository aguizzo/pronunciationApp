package dev.pronunciationAppBack.service;

import dev.pronunciationAppBack.model.AppUser;
import dev.pronunciationAppBack.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> getUserById(String id) {
        return appUserRepository.findById(id);
    }

    public AppUser createUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public AppUser updateUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public void deleteUser(String id) {
        appUserRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        appUserRepository.deleteAll();
    }

    public boolean existsById(String id) {
        return appUserRepository.existsById(id);
    }

    public long getUserCount() {
        return appUserRepository.count();
    }

    // Additional business logic can be added here
    public List<AppUser> getActiveUsers() {
        return appUserRepository.findByIsActiveTrue();
    }

    public AppUser getUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public List<AppUser> getUsersByAgeGreaterThan(int age) {
        return appUserRepository.findByAgeGreaterThan(age);
    }
}
