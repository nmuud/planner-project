package com.nmuud.zero.plannerproject.user.entity;

import com.nmuud.zero.plannerproject.user.security.Encryptor;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    private String name;
    private String email;
    private String password;
    private LocalDate birthday;

    @Builder
    public User(String name, String email, String password, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }


    public boolean matchesPassword(Encryptor encryptor, String password) {
        return encryptor.isMatch(password, this.getPassword());
    }
}
