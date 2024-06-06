package org.example.servlets.dto;

import org.example.model.Order;

import java.util.List;

public class ItemDTO {
    private int id;
    private String name;
    private int price;
    private List<Order> orders;

    public ItemDTO(int id, String name, int price, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.price = price;
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