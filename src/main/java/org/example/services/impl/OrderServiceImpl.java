package org.example.services.impl;

import org.example.model.Order;
import org.example.repository.impl.OrderRepositoryImpl;
import org.example.services.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderRepositoryImpl repository = new OrderRepositoryImpl();
    @Override
    public List<Order> getAll() {
        return repository.getAll();
    }

    @Override
    public Order get(int id) {
        return repository.get(id);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public void update(Order order) {
        repository.update(order);
    }

    @Override
    public void delete(int orderId) {
        repository.delete(orderId);
    }
}
