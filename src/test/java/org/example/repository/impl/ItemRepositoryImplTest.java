package org.example.repository.impl;

import db.BaseTest;
import org.example.model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemRepositoryImplTest extends BaseTest {

    @Test
    void getTest() throws IOException {
        Item saveItem = itemRepository.save(getItemFromJsonFile());
        Item getItem = itemRepository.get(saveItem.getId());
        assertEquals(getItem, getItemFromJsonFile());
    }

    @Test
    void getAllTest() {
        int size = 5;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Item item = new Item(i, "SomeItem" + i, i * 2, new ArrayList<>());
            items.add(item);
            itemRepository.save(item);
        }
        List<Item> getAllItems = itemRepository.getAll();
        assertEquals(getAllItems, items);
    }

    @Test
    void saveTest() throws IOException {
        Item saveItem = itemRepository.save(getItemFromJsonFile());
        Item getItem = itemRepository.get(saveItem.getId());
        assertEquals(getItem, getItemFromJsonFile());
    }

    @Test
    void updateTest() throws IOException {
        Item saveItem = itemRepository.save(getItemFromJsonFile());
        saveItem.setName("NewName");
        itemRepository.update(saveItem);
        Item getUpdateItem = itemRepository.get(saveItem.getId());
        assertEquals(getUpdateItem, saveItem);
    }

    @Test
    void deleteTest() throws IOException {
        Item saveItem = itemRepository.save(getItemFromJsonFile());
        assertEquals(itemRepository.get(saveItem.getId()), saveItem);
        itemRepository.delete(saveItem.getId());
        assertNull(itemRepository.get(saveItem.getId()));
    }
}