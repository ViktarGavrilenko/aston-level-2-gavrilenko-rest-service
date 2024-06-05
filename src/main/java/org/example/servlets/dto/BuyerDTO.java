package org.example.servlets.dto;

import org.example.model.Order;

import java.util.List;

public class BuyerDTO {
    private int id;
    private String name;
    private List<Order> orders;

    public BuyerDTO(int id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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