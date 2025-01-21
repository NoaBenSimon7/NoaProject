package com.example.noaproject.models;



import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    /// unique id of the cart
    private String id;

    private final ArrayList<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(String id) {
        this.id = id;
        items = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public Item removeItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    public Item getItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Item item : items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    public void clear() {
        items.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", items=" + items +
                '}';
    }


}
