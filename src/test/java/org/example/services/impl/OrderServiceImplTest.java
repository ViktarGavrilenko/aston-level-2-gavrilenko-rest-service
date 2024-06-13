package org.example.services.impl;

import org.example.model.Item;
import org.example.model.Order;
import org.example.repository.impl.OrderRepositoryImpl;
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
public class OrderServiceImplTest {
    @Mock
    private OrderRepositoryImpl orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    public static Order getTemplateOrder(int id) {
        List<Item> items = new ArrayList<>();
        Item item = new Item(id, "name" + id, id * 2, new ArrayList<>());
        items.add(item);
        return new Order(id, id, items);
    }

    public static List<Order> orderList(int size) {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            orderList.add(getTemplateOrder(i));
        }
        return orderList;
    }

    @Test
    void getAll() {
        when(orderRepository.getAll()).thenReturn(orderList(5));
        List<Order> getAllOrders = orderService.getAll();
        Assertions.assertEquals(getAllOrders, orderList(5));
    }

    @Test
    void get() {
        when(orderRepository.get(Mockito.anyInt())).thenReturn(getTemplateOrder(1));
        Order getOrder = orderService.get(1);
        Assertions.assertEquals(getOrder, getTemplateOrder(1));
    }

    @Test
    void save() {
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(getTemplateOrder(1));
        Order saveOrder = orderService.save(getTemplateOrder(1));
        Assertions.assertEquals(saveOrder, getTemplateOrder(1));
    }

    @Test
    void update() {
        orderService.update(getTemplateOrder(1));
        Mockito.verify(orderRepository).update(getTemplateOrder(1));
    }

    @Test
    void delete() {
        orderService.delete(1);
        Mockito.verify(orderRepository).delete(1);
    }
}