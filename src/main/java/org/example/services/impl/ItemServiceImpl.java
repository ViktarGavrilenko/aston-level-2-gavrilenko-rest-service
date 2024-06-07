package org.example.services.impl;

import org.example.model.Item;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.services.ItemService;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    private ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();

    @Override
    public List<Item> getAll() {
        return itemRepository.getAll();
    }

    @Override
    public Item get(int id) {
        return itemRepository.get(id);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Item item) {
        itemRepository.update(item);
    }

    @Override
    public void delete(int itemId) {
        itemRepository.delete(itemId);
    }
}