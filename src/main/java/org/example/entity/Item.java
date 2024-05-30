package org.example.entity;

import java.util.List;

public class Item {
    private String name;
    private int price;
    private List<Order> orders;

    public Item(String name, int price, List<Order> orders) {
        this.name = name;
        this.price = price;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
