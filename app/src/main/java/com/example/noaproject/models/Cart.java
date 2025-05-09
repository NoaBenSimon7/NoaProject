package com.example.noaproject.models;



import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    /// unique id of the cart


    protected  ArrayList<ItemCart> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(ArrayList<ItemCart> items) {
        this.items = items;
    }








    public ItemCart removeItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    public ItemCart getItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public ArrayList<ItemCart> getItems() {
        return this.items;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (ItemCart item : items) {
            totalPrice += item.getItem().getPrice()* item.getAmount();
        }
        return totalPrice;
    }

    public void clear() {
        items.clear();
    }


    public  void  addItemToCart(ItemCart itemCart){

           if(this.items==null)
             this.items=new ArrayList<>();


        this.items.add(itemCart);
    }

    public  void  delItemFromCart(ItemCart itemCart){

        if(itemCart!=null) {

            for (int i = 0; i < this.items.size(); i++) {
                if (this.items.get(i).getItem().getId().equals(itemCart.getItem().getId()))

                    this.items.remove(i);

            }

            this.items.remove(itemCart);
        }
    }


    public  void  updateItemCart(ItemCart itemCart){

        if(itemCart!=null) {

            for (int i = 0; i < this.items.size(); i++) {
                if (this.items.get(i).getItem().getId().equals(itemCart.getItem().getId()))

                    this.items.get(i).setAmount(itemCart.getAmount());

            }


        }
    }


//    public ArrayList<ItemCart> getItem() {
//
//        for (int i = 0; i < this.items.size(); i++) {
//            if (this.items.get(i).amount == 0)
//
//                this.items.remove(i);
//
//        }
//        return  this.items;
//
//    }


    public double getTotalCart(){
        double sum=0;
        for (int i=0; i<this.items.size();i++){
            sum+=this.items.get(i).amount*this.items.get(i).getItem().getPrice();



        }
        return sum;

    }







    @NonNull
    @Override
    public String toString() {
        return "Cart{" +

                ", items=" + items +
                '}';
    }


}
