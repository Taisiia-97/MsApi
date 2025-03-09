package com.example.taisiia.ms.controller;

import com.example.taisiia.ms.config.SecurityConfig;
import com.example.taisiia.ms.domain.dto.ItemDto;
import com.example.taisiia.ms.domain.dto.RegisterItemRequest;
import com.example.taisiia.ms.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@Import(SecurityConfig.class)
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    UserDetailsService userDetailsService;

    @Test
    void shouldGetItems() throws Exception {
        var login = "user29@gmail.com";
        var password = "Kotttkkkkkktttt2!";
        var token = generateToken(login);
        when(userDetailsService.loadUserByUsername(login)).thenReturn(
                new User(login, password, Set.of(new SimpleGrantedAuthority("USER"))));
        var itemUuid = UUID.randomUUID();
        var expectedItems = List.of(new ItemDto(itemUuid, "My item"));
        when(itemService.getItemsByUser()).thenReturn(expectedItems);

        mockMvc.perform(get("/items")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(itemUuid.toString()))
                .andExpect(jsonPath("$[0].name").value("My item"))
                .andReturn();
    }

    @Test
    void shouldNotGetItemsIfTokenIsInvalid() throws Exception {
        var token = "Empty ";

        mockMvc.perform(get("/items")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldCreateItem() throws Exception {
        var login = "user29@gmail.com";
        var password = "Kotttkkkkkktttt2!";
        var token = generateToken(login);
        var objectMapper = new ObjectMapper();
        var registerItemRequest = new RegisterItemRequest("My item");
        when(userDetailsService.loadUserByUsername(login)).thenReturn(
                new User(login, password, Set.of(new SimpleGrantedAuthority("USER"))));

        mockMvc.perform(post("/items")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerItemRequest))
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void shouldNotCreateItemsIfTokenIsInvalid() throws Exception {
        var token = "Empty ";
        var objectMapper = new ObjectMapper();
        var registerItemRequest = new RegisterItemRequest("My item");
        mockMvc.perform(post("/items")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerItemRequest))
                )
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    private String generateToken(String login) {
        String secret = "uSeNh1Y+V+v8U8WztPz8uX58Nd7JHjz8Fid4jVwFJxE=";
        var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Map<String, Object> claims = new HashMap<>();
        return "Bearer " + Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claims()
                .add(claims)
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .and()
                .signWith(secretKey)
                .compact();

    }
}