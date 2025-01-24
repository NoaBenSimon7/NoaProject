package com.example.noaproject.models;

import java.io.Serializable;

public class Item  implements Serializable {
    protected String id, itemName, type, size, color, fabric, imageRef,desc;
    protected double price;


    public Item(String id, String itemName, String type, String size, String color, String fabric, String desc, String imageRef, double price) {
        this.id = id;
        this.itemName = itemName;
        this.type = type;
        this.size = size;
        this.color = color;
        this.fabric = fabric;

        this.desc = desc;
        this.imageRef = imageRef;
        this.price = price;
    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", itemName='" + itemName + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", fabric='" + fabric + '\'' +
                ", imageRef='" + imageRef + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }
}