package org.example.servlets.mapper;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.servlets.dto.OrderDTO;
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
class OrderDtoMapperImplTest {

    @Mock
    ItemRepositoryImpl itemRepository;
    @InjectMocks
    OrderDtoMapperImpl dtoMapper;

    private OrderDTO getOrderDTO() {
        return new OrderDTO(1, 1, List.of(1, 2, 3));
    }

    private Order getOrder() {
        List<Item> items = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            List<Order> orders = new ArrayList<>();
            items.add(new Item(i, "name" + i, i * 2, orders));
        }
        return new Order(1, 1, items);
    }

    @Test
    void orderDTOToOrderTest() {
        Mockito.doAnswer(i -> {
            int number = i.getArgument(0);
            List<Order> orders = new ArrayList<>();
            return new Item(number, "name" + number, number * 2, orders);
        }).when(itemRepository).get(anyInt());
        Assertions.assertEquals(dtoMapper.orderDTOToOrder(getOrderDTO()), getOrder());
    }

    @Test
    void orderToOrderDTOTest() {
        Assertions.assertEquals(dtoMapper.orderToOrderDTO(getOrder()), getOrderDTO());
    }
}