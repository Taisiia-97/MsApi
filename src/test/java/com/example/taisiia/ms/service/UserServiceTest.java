package com.example.taisiia.ms.service;

import com.example.taisiia.ms.domain.dao.User;
import com.example.taisiia.ms.domain.dto.RegisterUserRequest;
import com.example.taisiia.ms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserService userService = new UserService(bCryptPasswordEncoder, userRepository);

    @Test
    void shouldRegisterUserAndSaveToRepository() {
        // given
        var request = new RegisterUserRequest("user29@gmail.com", "password123!");
        var encodedPassword = "encodedPassword";

        when(bCryptPasswordEncoder.encode(request.password())).thenReturn(encodedPassword);

        // when
        userService.registerUser(request);

        // then
        var userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        var savedUser = userCaptor.getValue();
        assertEquals(request.login(), savedUser.getLogin());
        assertEquals(encodedPassword, savedUser.getPassword());
    }
}