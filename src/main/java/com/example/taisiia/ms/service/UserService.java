package com.example.taisiia.ms.service;

import com.example.taisiia.ms.domain.dao.User;
import com.example.taisiia.ms.domain.dto.RegisterUserRequest;
import com.example.taisiia.ms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public void registerUser(RegisterUserRequest registerUserRequest) {
        var user = User.builder()
                .login(registerUserRequest.login())
                .password(bCryptPasswordEncoder.encode(registerUserRequest.password()))
                .build();
        userRepository.save(user);
    }
}
