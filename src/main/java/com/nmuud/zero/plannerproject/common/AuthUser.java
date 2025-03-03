package com.nmuud.zero.plannerproject.common;

import lombok.Getter;

@Getter
public class AuthUser {
    private final Long id;

    private AuthUser(Long id) {
        this.id = id;
    }

    public static AuthUser of(Long id) {
        return new AuthUser(id);
    }
}
