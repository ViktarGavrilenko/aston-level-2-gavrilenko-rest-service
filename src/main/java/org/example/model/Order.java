package org.example.model;

import java.util.List;

public class Order {
    private int number;
    private List<Item> items;

    public Order(int number, List<Item> items) {
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

    @Override
    public String toString() {
        return "Order{" +
                "number=" + number +
                ", items=" + items +
                '}';
    }
}
