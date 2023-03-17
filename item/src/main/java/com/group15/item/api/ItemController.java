package com.group15.item.api;

import com.group15.item.model.Item;
import com.group15.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("get-all-items")
    public List<Item> getItems() {
        return itemService.getAllItems();
    }

    static record GetItemRequest(
            Integer itm_id
    ) {}
    @GetMapping("get-item")
    public Item getItem(@RequestBody GetItemRequest request) {
        return itemService.getItemById(request.itm_id);
    }


}


