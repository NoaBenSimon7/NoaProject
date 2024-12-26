package com.example.noaproject.models;

import java.io.Serializable;

public class ItemOrder  extends Item implements Serializable {

    protected int amount;





    public ItemOrder() {

    }

    public ItemOrder(String id, String name, String type, String size, String color, String fabric, String pic, double price, int amount) {
        super(id, name, type, size, color, fabric, pic, price);
        this.amount = amount;
    }

    public ItemOrder(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemOrder{" +
                "amount=" + amount +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", fabric='" + fabric + '\'' +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                '}';
    }
}
