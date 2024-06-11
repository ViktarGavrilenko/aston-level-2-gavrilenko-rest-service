package org.example.servlets.mapper;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.OrderRepositoryImpl;
import org.example.servlets.dto.ItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class ItemDtoMapperImplTest {

    @Mock
    OrderRepositoryImpl orderRepository;
    @InjectMocks
    ItemDtoMapperImpl dtoMapper;

    private ItemDTO getItemDTO() {
        return new ItemDTO(1, "name", 1, List.of(1, 2, 3));
    }

    private Item getItem() {
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            List<Item> items = new ArrayList<>();
            orders.add(new Order(i, i, items));
        }
        return new Item(1, "name", 1, orders);
    }

    @Test
    void itemDTOToItemTest() {
        Mockito.doAnswer(i -> {
            int number = i.getArgument(0);
            List<Item> items = new ArrayList<>();
            return new Order(number, number, items);
        }).when(orderRepository).get(anyInt());
        Assertions.assertEquals(dtoMapper.itemDTOToItem(getItemDTO()), getItem());
    }

    @Test
    void itemToItemDTOTest() {
        Assertions.assertEquals(dtoMapper.itemToItemDTO(getItem()), getItemDTO());
    }
}