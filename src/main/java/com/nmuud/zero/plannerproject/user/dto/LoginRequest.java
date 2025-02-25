package com.nmuud.zero.plannerproject.user.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private final String email;
    private final String password;
}
