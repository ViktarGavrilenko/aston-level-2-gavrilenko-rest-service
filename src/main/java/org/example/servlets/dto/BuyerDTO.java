package org.example.servlets.dto;

import java.util.List;

public class BuyerDTO {
    private String name;
    private List<Integer> orders;

    public BuyerDTO(String name, List<Integer> orders) {
        this.name = name;
        this.orders = orders;
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
