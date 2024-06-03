package org.example.servlets.dto;

import org.example.model.Order;

import java.util.List;

public class ItemDTO {
    private String name;
    private int price;
    private List<Order> orders;

    public ItemDTO(String name, int price, List<Order> orders) {
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
