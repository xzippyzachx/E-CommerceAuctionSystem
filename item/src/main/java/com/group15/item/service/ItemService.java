package com.group15.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group15.item.model.Item;
import com.group15.item.repository.ItemRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    protected final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    public Item getItemById(Integer itm_id) {
        try {
            return itemRepository.findById(itm_id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Item> getItemsByKeyword(String keyword) {
        keyword = keyword.toLowerCase();
        return itemRepository.findByItmNameContainingOrItmDescriptionContaining(keyword);
    }

    public List<Item> getItemsByIds(Integer[] itmIds) {
        return itemRepository.findAllById(Arrays.stream(itmIds).toList());
    }

    public void resetItemData() {
        itemRepository.resetItemData();
    }
}
