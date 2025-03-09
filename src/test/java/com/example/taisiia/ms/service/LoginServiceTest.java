package com.example.taisiia.ms.service;

import com.example.taisiia.ms.domain.dto.AuthenticationRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginServiceTest {
    private final String secret = "uSeNh1Y+V+v8U8WztPz8uX58Nd7JHjz8Fid4jVwFJxE=";

    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    private final LoginService loginService = new LoginService(authenticationManager, secretKey);

    @Test
    void shouldGenerateTokenUponSuccessfulAuthentication() {
        // given
        var authRequest = new AuthenticationRequest("user29@gmail.com", "Kotttkkkkkktttt2!");
        var authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user29@gmail.com");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        Map<String, Object> claims = new HashMap<>();
        var expectedToken = Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claims()
                .add(claims)
                .subject(authentication.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .and()
                .signWith(secretKey)
                .compact();

        // when
        var tokenDto = loginService.log(authRequest);

        // then
        assertEquals(expectedToken, tokenDto.token());
    }

    @Test
    void shouldNotGenerateTokenUponNotSuccessfulAuthentication() {
        // given
        var authRequest = new AuthenticationRequest("user29@gmail.com", "BadPassword#");
        var authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user29@gmail.com");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // when
        var badCredentialsException = assertThrows(BadCredentialsException.class, () -> loginService.log(authRequest));

        // then
        assertThat(badCredentialsException).hasMessage("Bad credentials");
    }
}