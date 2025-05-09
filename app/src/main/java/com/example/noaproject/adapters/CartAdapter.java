package com.example.noaproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noaproject.R;
import com.example.noaproject.models.Cart;
import com.example.noaproject.models.Item;
import com.example.noaproject.models.ItemCart;
import com.example.noaproject.screens.CartActivity;
import com.example.noaproject.screens.ItemDetailActivity;
import com.example.noaproject.utils.ImageUtil;
import com.google.android.gms.common.api.Api;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private Cart cart;

    public CartAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the oneitem layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onerowcart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCart itemCart = cart.getItems().get(position);
        if (itemCart == null) return;


        int amount = itemCart.getAmount();

        if (amount > 0) {


            holder.tvName.setText(itemCart.getItem().getItemName());

            holder.tvPrice.setText("מחיר: " + itemCart.getItem().getPrice());


            holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(itemCart.getItem().getImageRef()));
        }
            // הוספתי את הלוגיקה למעבר למסך פרטי המוצר כאשר לוחצים על פריט
            holder.btnDel.setOnClickListener(v -> {

                cart.delItemFromCart(itemCart);
                ((CartActivity) context).goUpdateCart(cart);


            });



        holder.btnPlus.setOnClickListener(v -> {
            itemCart.setAmount(itemCart.getAmount() + 1);
            //  item_amount.setText(String.valueOf(item_cart.getAmount()));

            cart.updateItemCart(itemCart);
            ((CartActivity) context).goUpdateCart(cart); //

        });

        holder.btnMinnus.setOnClickListener(v -> {
            if (itemCart.getAmount() > 0) {
                itemCart.setAmount(itemCart.getAmount() - 1);
                cart.updateItemCart(itemCart);
                holder.tvAmount.setText(String.valueOf(itemCart.getAmount()));

                ((CartActivity) context).goUpdateCart(cart); //

            } else if (itemCart.getAmount() == 0) {

                cart.getItems();
                cart.delItemFromCart(itemCart);

                ((CartActivity) context).goUpdateCart(cart); //


            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }





        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView tvName;

            public final TextView tvPrice;

            public final ImageView itemImageView;
            public final Button btnDel, btnMinnus, btnPlus;
            public final TextView tvAmount;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvItemName);

                tvPrice = itemView.findViewById(R.id.tvItemPrice);
                itemImageView = itemView.findViewById(R.id.ivItemImageRef);
                btnPlus = itemView.findViewById(R.id.btnPlus);
                tvAmount = itemView.findViewById(R.id.tvItemAmountCart);
                btnMinnus = itemView.findViewById(R.id.btnMinus);
                btnDel = itemView.findViewById(R.id.btnDel);


            }
        }
    }

