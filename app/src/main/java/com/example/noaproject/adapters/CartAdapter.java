package com.example.noaproject.adapters;

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
import com.example.noaproject.screens.ItemDetailActivity;
import com.example.noaproject.utils.ImageUtil;
import com.google.android.gms.common.api.Api;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final Cart cart;

    public CartAdapter(Cart cart) {
        this.cart = cart;
    };


    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the oneitem layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onerowcart, parent, false);
        return new ItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        ItemCart itemCart = cart.getItems().get(position);
        if (itemCart == null) return;



        holder.tvName.setText(itemCart.getItem().getItemName());

        holder.tvPrice.setText("מחיר: " + itemCart.getItem().getPrice());



        holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(itemCart.getItem().getImageRef()));

        // הוספתי את הלוגיקה למעבר למסך פרטי המוצר כאשר לוחצים על פריט
        holder.btnGoDetails.setOnClickListener(v -> {
            // יצירת Intent למעבר למסך פרטי המוצר
//            Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
  //           הוספת המידע של המוצר ל-Intent
//            intent.putExtra("item", itemCart);
//            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cart.getItems().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;

        public final TextView tvPrice;

        public final ImageView itemImageView;
        public final Button btnGoDetails;
        public final TextView tvAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);

            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            itemImageView = itemView.findViewById(R.id.ivItemImageRef);
            btnGoDetails = itemView.findViewById(R.id.btnGoToDetails);
            tvAmount=itemView.findViewById(R.id.tvItemAmountCart);


        }
    }
}
