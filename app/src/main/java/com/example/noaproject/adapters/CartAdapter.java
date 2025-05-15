package com.example.noaproject.adapters;

import android.content.Context;
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
import com.example.noaproject.models.ItemCart;
import com.example.noaproject.screens.CartActivity;
import com.example.noaproject.utils.ImageUtil;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private Cart cart;

    public CartAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart != null ? cart : new Cart();
    }

    public void setCart(Cart cart) {
        this.cart = cart != null ? cart : new Cart();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onerowcart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCart itemCart = cart.getItems().get(position);
        if (itemCart == null) return;

        holder.tvName.setText(itemCart.getItem().getItemName());
        holder.tvPrice.setText("מחיר: " + itemCart.getItem().getPrice());
        holder.tvAmount.setText(String.valueOf(itemCart.getAmount()));
        holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(itemCart.getItem().getImageRef()));

        holder.btnDel.setOnClickListener(v -> {
            cart.delItemFromCart(itemCart);
            notifyDataSetChanged();
            ((CartActivity) context).goUpdateCart(cart);
        });

        holder.btnPlus.setOnClickListener(v -> {
            itemCart.setAmount(itemCart.getAmount() + 1);
            cart.updateItemCart(itemCart);
            notifyDataSetChanged();
            ((CartActivity) context).goUpdateCart(cart);
        });

        holder.btnMinnus.setOnClickListener(v -> {
            if (itemCart.getAmount() > 1) {
                itemCart.setAmount(itemCart.getAmount() - 1);
                cart.updateItemCart(itemCart);
            } else {
                cart.delItemFromCart(itemCart);
            }
            notifyDataSetChanged();
            ((CartActivity) context).goUpdateCart(cart);
        });
    }

    @Override
    public int getItemCount() {
        return cart != null && cart.getItems() != null ? cart.getItems().size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName, tvPrice, tvAmount;
        public final ImageView itemImageView;
        public final Button btnDel, btnMinnus, btnPlus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            tvAmount = itemView.findViewById(R.id.tvItemAmountCart);
            itemImageView = itemView.findViewById(R.id.ivItemImageRef);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinnus = itemView.findViewById(R.id.btnMinus);
            btnDel = itemView.findViewById(R.id.btnDel);
        }
    }
}
