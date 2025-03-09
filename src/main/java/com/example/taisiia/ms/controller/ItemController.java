package com.example.taisiia.ms.controller;

import com.example.taisiia.ms.domain.dto.ItemDto;
import com.example.taisiia.ms.domain.dto.RegisterItemRequest;
import com.example.taisiia.ms.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(description = "Get a list of current user's items.")
    @ApiResponses({
            @ApiResponse(description = "List of items", responseCode = "200", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ItemDto.class),
                    examples = @ExampleObject(name = "Items list",
                            value = """
                                    [
                                      {
                                        "id": "3ee09787-29ea-4adf-8570-28f1999fb17f",
                                        "nane": "My item"
                                      }
                                    ]
                                    """)
            )),
            @ApiResponse(description = "You have not provided an authentication token, the one provided has expired, was revoked or is not authentic.", responseCode = "401")}
    )
    @GetMapping
    public List<ItemDto> getItemsByUser() {
        return itemService.getItemsByUser();
    }

    @Operation(description = "Create a new item.")
    @ApiResponses({
            @ApiResponse(description = "Item created successfully.", responseCode = "200"),
            @ApiResponse(description = "You have not provided an authentication token, the one provided has expired, was revoked or is not authentic.", responseCode = "401")}
    )
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createItem(@Valid @RequestBody RegisterItemRequest registerItemRequest) {
        itemService.createItem(registerItemRequest);
    }
}
