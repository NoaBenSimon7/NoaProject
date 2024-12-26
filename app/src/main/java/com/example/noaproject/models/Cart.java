package com.example.noaproject.models;



import java.io.Serializable;
import java.util.ArrayList;

public class Cart  implements Serializable {

    protected ArrayList<ItemOrder> itemOrders;

    public Cart(ArrayList<ItemOrder> itemOrders) {
        this.itemOrders = itemOrders;
    }

    public Cart() {
    }

    public ArrayList<ItemOrder> getItemOrders() {
        return itemOrders;
    }

    public void setItemOrders(ArrayList<ItemOrder> itemOrders) {
        this.itemOrders = itemOrders;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "itemOrders=" + itemOrders +
                '}';
    }
}
