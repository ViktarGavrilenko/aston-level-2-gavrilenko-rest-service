package org.example.servlets.dto;

import org.example.model.Item;

import java.util.List;

public class OrderDTO {
    private int number;
    private List<Item> items;

    public OrderDTO(int number, List<Item> items) {
        this.number = number;
        this.items = items;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
