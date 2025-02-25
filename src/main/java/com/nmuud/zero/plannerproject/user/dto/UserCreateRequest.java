package com.nmuud.zero.plannerproject.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateRequest {

    private final String name;
    private final String email;
    private final String password;
    private final LocalDate birthday;
}
