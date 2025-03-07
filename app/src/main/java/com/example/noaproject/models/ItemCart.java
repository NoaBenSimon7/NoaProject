package com.example.noaproject.models;

public class ItemCart {

    Item item;
    int amount;

    public ItemCart(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public ItemCart() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemCart{" +
                "item=" + item +
                ", amount=" + amount +
                '}';
    }
}
