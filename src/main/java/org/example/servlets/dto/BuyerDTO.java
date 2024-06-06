package org.example.servlets.dto;

import org.example.model.Order;

import java.util.List;

public class BuyerDTO {
    private int id;
    private String name;
    private List<Integer> orders;

    public BuyerDTO(int id, String name, List<Integer> orders) {
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

    public List<Integer> getOrders() {
        return orders;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }
}