package dev.pronunciationAppBack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private int age;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    public User(String id, String name, String email, int age, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.joinDate = joinDate != null ? joinDate : LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format("User{id='%s', name='%s', email='%s', age=%d, joinDate=%s}",
                id, name, email, age, joinDate);
    }
}
