package com.example.noaproject.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noaproject.R;
import com.example.noaproject.models.Item;
import com.example.noaproject.utils.ImageUtil;
import com.example.noaproject.screens.ItemDetailActivity;  // הוספתי את ה-import למסך הפרטים

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final List<Item> itemList;

    public ItemsAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the oneitem layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        if (item == null) return;

        holder.tvName.setText(item.getItemName());
        holder.tvSize.setText("מידה:  " + item.getSize());
        holder.tvType.setText("פריט:  " + item.getType());
        holder.tvColor.setText("צבע:  " + item.getColor());
        holder.tvFabric.setText("סוג:  " + item.getFabric());
        holder.tvPrice.setText("מחיר: " + item.getPrice());
        holder.tvDesc.setText("תיאור:  " + item.getDesc());

        holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(item.getImageRef()));

        // הוספתי את הלוגיקה למעבר למסך פרטי המוצר כאשר לוחצים על פריט
        holder.itemView.setOnClickListener(v -> {
            // יצירת Intent למעבר למסך פרטי המוצר
            Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
            // הוספת המידע של המוצר ל-Intent
            intent.putExtra("item", item);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final TextView tvType;
        public final TextView tvSize;
        public final TextView tvColor;
        public final TextView tvFabric;
        public final TextView tvPrice;
        public final TextView tvDesc;
        public final ImageView itemImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvType = itemView.findViewById(R.id.tvItemType);
            tvSize = itemView.findViewById(R.id.tvItemSize);
            tvColor = itemView.findViewById(R.id.tvItemColor);
            tvFabric = itemView.findViewById(R.id.tvItemFabric);
            tvDesc = itemView.findViewById(R.id.tvItemDesc);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            itemImageView = itemView.findViewById(R.id.ivItemImageRef);
        }
    }
}
