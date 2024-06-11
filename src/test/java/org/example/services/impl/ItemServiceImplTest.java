package org.example.services.impl;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepositoryImpl itemRepository;
    @InjectMocks
    private ItemServiceImpl itemService;

    private Item getTemplateItem(int id) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(id, id, new ArrayList<>());
        orders.add(order);
        return new Item(id, "name" + id, id * 3, orders);
    }

    private List<Item> itemList(int size) {
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            itemList.add(getTemplateItem(i));
        }
        return itemList;
    }

    @Test
    void getAll() {
        when(itemRepository.getAll()).thenReturn(itemList(5));
        List<Item> getAllItem = itemService.getAll();
        Assertions.assertEquals(getAllItem, itemList(5));
    }

    @Test
    void get() {
        when(itemRepository.get(Mockito.anyInt())).thenReturn(getTemplateItem(1));
        Item getItem = itemService.get(1);
        Assertions.assertEquals(getItem, getTemplateItem(1));
    }

    @Test
    void save() {
        when(itemRepository.save(Mockito.any(Item.class))).thenReturn(getTemplateItem(1));
        Item saveItem = itemService.save(getTemplateItem(1));
        Assertions.assertEquals(saveItem, getTemplateItem(1));
    }

    @Test
    void update() {
        itemService.update(getTemplateItem(1));
        Mockito.verify(itemRepository).update(getTemplateItem(1));
    }

    @Test
    void delete() {
        itemService.delete(1);
        Mockito.verify(itemRepository).delete(1);
    }
}