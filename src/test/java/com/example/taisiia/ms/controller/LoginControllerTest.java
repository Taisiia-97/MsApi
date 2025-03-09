package com.example.taisiia.ms.controller;

import com.example.taisiia.ms.config.SecurityConfig;
import com.example.taisiia.ms.domain.dto.AuthenticationRequest;
import com.example.taisiia.ms.domain.dto.TokenDto;
import com.example.taisiia.ms.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoginService loginService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void shouldLogUser() throws Exception {
        var authenticationRequest = new AuthenticationRequest("user29!@gmail.com", "Kotttkkkkkktttt2!");
        var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMjlAZ21haWwuY29tIiwiaWF0IjoxNzQxNTA3MDI1LCJleHAiOjE3NDE1MTA2MjV9.Tb8BnuvTUdV6_yP0e_UhXygV1wzb-wVJ5kzHSwJ9QLw";
        var tokenDto = new TokenDto(token);
        when(loginService.log(authenticationRequest)).thenReturn(tokenDto);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.token").value(tokenDto.token()))
                .andReturn();
    }

    @Test
    void shouldNotLogUser() throws Exception {
        var authenticationRequest = new AuthenticationRequest("user29!@gmail.com", "BadPassword!");
        when(loginService.log(authenticationRequest))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("Bad credentials"))
                .andReturn();
    }
}