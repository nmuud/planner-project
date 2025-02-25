package com.nmuud.zero.plannerproject.user.service;

import com.nmuud.zero.plannerproject.user.entity.User;
import com.nmuud.zero.plannerproject.user.dto.UserCreateRequest;
import com.nmuud.zero.plannerproject.user.repository.UserRepository;
import com.nmuud.zero.plannerproject.user.security.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Encryptor encryptor;
    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserCreateRequest userCreateRequest) {
        userRepository.findByEmail(userCreateRequest.getEmail())
                .ifPresent(u -> {throw new RuntimeException("user already existed!");

                });

        return userRepository.save(new User(
                userCreateRequest.getName(),
                userCreateRequest.getEmail(),
                encryptor.encrypt(userCreateRequest.getPassword()),
                userCreateRequest.getBirthday()
        ));
    }

    @Transactional
    public Optional<User> authenticateUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.matchesPassword(encryptor, password) ? user : null);
    }

    public User findByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bad Request"));
    }
}
