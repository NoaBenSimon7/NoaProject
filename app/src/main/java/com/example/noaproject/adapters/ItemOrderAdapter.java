package com.example.noaproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.noaproject.R;
import com.example.noaproject.models.Item;
import com.example.noaproject.models.ItemCart;
import com.example.noaproject.utils.ImageUtil;

import java.util.List;

public class ItemOrderAdapter  extends RecyclerView.Adapter<ItemOrderAdapter.ViewHolder> {
    private Context context;
    private List<ItemCart> items;

    public ItemOrderAdapter(Context context, List<ItemCart> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.oneitemorder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ItemCart item_cart = items.get(position);
        holder.bind(item_cart);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name, product_price, product_amount, product_size;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_imageItemOrder);
            product_name = itemView.findViewById(R.id.product_nameItemOrder);
            product_price = itemView.findViewById(R.id.product_priceItemOrder);
            product_amount = itemView.findViewById(R.id.product_AmountItemOrder);
            product_size = itemView.findViewById(R.id.product_sizeItemOrder);

        }

        public void bind(final ItemCart item_cart) {
            Item item = item_cart.getItem();
            int amount = item_cart.getAmount();



                product_image.setImageBitmap(ImageUtil.convertFrom64base(item.getImageRef()));
                product_name.setText(item.getItemName());
                product_price.setText(item.getPrice() + "₪");
                product_amount.setText(String.valueOf(amount));









            }

        }
    }

