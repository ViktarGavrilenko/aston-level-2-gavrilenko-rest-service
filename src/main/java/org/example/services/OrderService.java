package org.example.services;

import org.example.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order get(int id);

    Order save(Order order);

    void update(Order order);

    void delete(int orderId);
}
