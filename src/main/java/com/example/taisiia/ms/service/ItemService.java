package com.example.taisiia.ms.service;

import com.example.taisiia.ms.domain.dao.Item;
import com.example.taisiia.ms.domain.dto.ItemDto;
import com.example.taisiia.ms.domain.dto.RegisterItemRequest;
import com.example.taisiia.ms.repository.ItemRepository;
import com.example.taisiia.ms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public void createItem(RegisterItemRequest registerItemRequest) {
        var login = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByLogin(login);
        var item = Item.builder()
                .user(user.get())
                .name(registerItemRequest.name())
                .build();
        itemRepository.save(item);
    }

    public List<ItemDto> getItemsByUser() {
        var login = SecurityContextHolder.getContext().getAuthentication().getName();
        return itemRepository.findByUserLogin(login)
                .stream()
                .map(item -> new ItemDto(item.getId(), item.getName()))
                .toList();
    }
}
