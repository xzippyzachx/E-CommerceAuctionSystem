package com.group15.item.repository;

import com.group15.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByItmNameContainingOrItmDescriptionContaining(String keyword, String keyword1);
}
