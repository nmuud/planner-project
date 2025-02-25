package com.nmuud.zero.plannerproject.user.security;

public interface Encryptor {

    public String encrypt(String origin);

    boolean isMatch(String origin, String hashed);
}
