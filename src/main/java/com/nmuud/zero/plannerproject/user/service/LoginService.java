package com.nmuud.zero.plannerproject.user.service;

import com.nmuud.zero.plannerproject.user.dto.LoginRequest;
import com.nmuud.zero.plannerproject.user.dto.SignUpRequest;
import com.nmuud.zero.plannerproject.user.dto.UserCreateRequest;
import com.nmuud.zero.plannerproject.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    public final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    @Transactional
    public void signUp(SignUpRequest signUpRequest, HttpSession session) {

        final User user = userService.createUser(new UserCreateRequest(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getBirthday()
        ));
        session.setAttribute(LOGIN_SESSION_KEY, user.getId());
    }

    @Transactional
    public void login(LoginRequest loginRequest, HttpSession session) {

        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if (userId != null) {
            return;
        }
        final Optional<User> user =
                userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        } else {
            throw new RuntimeException("Bad Request");
        }
    }

    @Transactional
    public void logout(HttpSession session) {

        session.removeAttribute(LOGIN_SESSION_KEY);
    }
}
