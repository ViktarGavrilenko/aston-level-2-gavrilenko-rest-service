package org.example.entity;

import java.util.List;

public class Buyer {
    private String name;
    private List<Order> orders;

    public Buyer(List<Order> orders, String name) {
        this.orders = orders;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
