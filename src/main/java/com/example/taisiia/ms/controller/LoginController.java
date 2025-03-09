package com.example.taisiia.ms.controller;

import com.example.taisiia.ms.domain.dto.AuthenticationRequest;
import com.example.taisiia.ms.domain.dto.TokenDto;
import com.example.taisiia.ms.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(description = "Authenticate with the platform.")
    @ApiResponses(@ApiResponse(description = "Logged in successfully.", responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TokenDto.class),
                    examples = {
                            @ExampleObject(name = "Token example", value = """
                                    {
                                    "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSm9obiBEb2UiLCJyb2xlIjoiU3R1ZGVudCJ9.IxBkuQHrrwJrc8_IA5DPdGhBKx43iYsricXKXUQt_8o"
                                    }
                                    """)
                    })))
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TokenDto log(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return loginService.log(authenticationRequest);
    }
}
