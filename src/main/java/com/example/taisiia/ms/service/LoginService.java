package com.example.taisiia.ms.service;

import com.example.taisiia.ms.domain.dto.AuthenticationRequest;
import com.example.taisiia.ms.domain.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;

    public TokenDto log(AuthenticationRequest authenticationRequest) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.login(), authenticationRequest.password());
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Map<String, Object> claims = new HashMap<>();
        var token = Jwts.builder()
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
        return new TokenDto(token);
    }
}
