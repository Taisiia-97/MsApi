package com.example.taisiia.ms.controller;

import com.example.taisiia.ms.domain.dto.RegisterUserRequest;
import com.example.taisiia.ms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterUserController {

    private final UserService userService;

    @Operation(description = "Register to the platform.")
    @ApiResponses(@ApiResponse(description = "Registering successfully.", responseCode = "204"))
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        userService.registerUser(registerUserRequest);
    }
}
