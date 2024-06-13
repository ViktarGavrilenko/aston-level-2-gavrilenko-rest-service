package org.example.repository.impl;

import db.BaseTest;
import org.example.model.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderRepositoryImplTest extends BaseTest {

    @Test
    void getTest() throws IOException {
        System.out.println(orderRepository.getAll().size());
        Order saveOrder = orderRepository.save(getOrderFromJsonFile());
        Order getOrder = orderRepository.get(saveOrder.getId());
        assertEquals(getOrder, getOrderFromJsonFile());
    }

    @Test
    void getAllTest() {
        int size = 5;
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Order order = new Order(i, i * 2, new ArrayList<>());
            orders.add(order);
            orderRepository.save(order);
        }
        List<Order> getAllOrders = orderRepository.getAll();
        assertEquals(getAllOrders, orders);
    }

    @Test
    void saveTest() throws IOException {
        Order saveOrder = orderRepository.save(getOrderFromJsonFile());
        Order getOrder = orderRepository.get(saveOrder.getId());
        assertEquals(getOrder, getOrderFromJsonFile());
    }

    @Test
    void updateTest() throws IOException {
        Order saveOrder = orderRepository.save(getOrderFromJsonFile());
        saveOrder.setNumber(33333);
        orderRepository.update(saveOrder);
        Order getUpdateItem = orderRepository.get(saveOrder.getId());
        assertEquals(getUpdateItem, saveOrder);
    }

    @Test
    void deleteTest() throws IOException {
        Order saveOrder = orderRepository.save(getOrderFromJsonFile());
        assertEquals(orderRepository.get(saveOrder.getId()), saveOrder);
        orderRepository.delete(saveOrder.getId());
        assertNull(orderRepository.get(saveOrder.getId()));
    }
}