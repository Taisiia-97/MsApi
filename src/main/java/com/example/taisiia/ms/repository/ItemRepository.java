package com.example.taisiia.ms.repository;

import com.example.taisiia.ms.domain.dao.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.rmi.server.UID;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, UID> {

    List<Item> findByUserLogin(String login);
}
