package com.example.noaproject.models;

import java.io.Serializable;

public class Item  implements Serializable {
    protected String id, itemName, type, size, color, fabric, pic,dedc, imageRef;
    protected double price;



    public Item(String id, String name, String type, String size, String color, String fabric, String pic, double price) {
        this.id = id;
        this.itemName = name;
        this.type = type;
        this.size = size;
        this.color = color;
        this.fabric = fabric;
        this.pic = pic;
        this.price = price;
    }
    public Item(String itemName,String type, String imageRef, String dedc,String price){
        this.itemName = itemName;
        this.type = type;
        this.imageRef = imageRef;
        this.dedc = dedc;
        this.price = Double.parseDouble(price);

    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }



    public void setPrice(Integer price) {
        this.price = price;
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
