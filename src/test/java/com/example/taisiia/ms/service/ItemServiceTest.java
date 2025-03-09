package com.example.taisiia.ms.service;

import com.example.taisiia.ms.domain.dao.Item;
import com.example.taisiia.ms.domain.dao.User;
import com.example.taisiia.ms.domain.dto.ItemDto;
import com.example.taisiia.ms.domain.dto.RegisterItemRequest;
import com.example.taisiia.ms.repository.ItemRepository;
import com.example.taisiia.ms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ItemServiceTest {

    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ItemService itemService = new ItemService(itemRepository, userRepository);

    @Test
    void shouldCreateItem() {
        // given
        var registerItemRequest = new RegisterItemRequest("My item");
        var login = "user29!@gmail.com";
        var securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        var authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(login);
        var uuid = UUID.randomUUID();
        var user = Optional.of(new User(uuid, login, "Password12!"));
        when(userRepository.findByLogin(login)).thenReturn(user);

        // when
        itemService.createItem(registerItemRequest);

        // then
        var itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository, times(1)).save(itemCaptor.capture());

        var savedItem = itemCaptor.getValue();
        assertEquals(registerItemRequest.name(), savedItem.getName());
        assertEquals(user.get(), savedItem.getUser());
    }

    @Test
    void shouldGetItems() {
        // given
        var login = "user29!@gmail.com";
        var securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        var authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(login);
        var userUuid = UUID.randomUUID();
        var user = new User(userUuid, login, "Password12!");
        var uuid1 = UUID.randomUUID();
        var uuid2 = UUID.randomUUID();
        var items = List.of(new Item(uuid1, user, "My item 1"),
                new Item(uuid2, user, "My item 2"));
        when(itemRepository.findByUserLogin(login)).thenReturn(items);
        var expectedItems = List.of(new ItemDto(uuid1, "My item 1"),
                new ItemDto(uuid2, "My item 2"));

        // when
        var itemsResult = itemService.getItemsByUser();

        // then
        assertThat(itemsResult).containsExactlyInAnyOrderElementsOf(expectedItems);
    }
}