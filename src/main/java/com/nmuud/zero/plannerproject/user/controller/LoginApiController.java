package com.nmuud.zero.plannerproject.user.controller;

import com.nmuud.zero.plannerproject.user.dto.LoginRequest;
import com.nmuud.zero.plannerproject.user.service.LoginService;
import com.nmuud.zero.plannerproject.user.dto.SignUpRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/api/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest, HttpSession httpSession) {
        loginService.signUp(signUpRequest, httpSession);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        loginService.login(loginRequest, httpSession);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(HttpSession httpSession) {
        loginService.logout(httpSession);
        return ResponseEntity.ok().build();
    }
}
