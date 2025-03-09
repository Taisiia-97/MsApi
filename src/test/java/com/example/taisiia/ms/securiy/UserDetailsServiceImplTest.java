package com.example.taisiia.ms.securiy;

import com.example.taisiia.ms.domain.dao.User;
import com.example.taisiia.ms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userRepository);

    @Test
    void shouldLoadUserByUsername() {
        // given
        var login = "user29@gmail.com";
        var password = "Kotttkkkkkktttt2!";
        var uuid = UUID.randomUUID();
        var user = new User(uuid, login, password);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        // when
        var userDetails = userDetailsService.loadUserByUsername(login);

        // then
        assertNotNull(userDetails);
        assertEquals(login, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserNotFound() {
        // given
        String username = "nonexistentuser@gmail.com";
        when(userRepository.findByLogin(username)).thenReturn(Optional.empty());

        // when
        var usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        //then
        assertThat(usernameNotFoundException).hasMessage("User doesn't exist");
    }
}