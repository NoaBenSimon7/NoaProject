package com.example.noaproject.adapters;

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

import java.util.List;


/// Adapter for the items recycler view
/// @see RecyclerView
/// @see Item
/// @see R.layout#item_selected_item
    public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

        /// list of items
        /// @see Item
        private final List<Item> itemList;

        public ItemsAdapter(List<Item> itemList) {
            this.itemList = itemList;
        }

        /// create a view holder for the adapter
        /// @param parent the parent view group
        /// @param viewType the type of the view
        /// @return the view holder
        /// @see ViewHolder
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            /// inflate the item_selected_item layout
            /// @see R.layout.item_selected_item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneitem, parent, false);
            return new ViewHolder(view);
        }


        /// bind the view holder with the data
        /// @param holder the view holder
        /// @param position the position of the item in the list
        /// @see ViewHolder
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Item item = itemList.get(position);
            if (item == null) return;

            holder.tvName.setText(item.getItemName());

            holder.tvSize.setText( "מידה:  " + item.getSize());
            holder.tvType.setText( "פריט:  " + item.getType());
            holder.tvColor.setText( "צבע:  " + item.getColor());
            holder.tvFabric.setText( "סוג:  " + item.getFabric());
            holder.tvPrice.setText("מחיר: " + item.getPrice());
            holder.tvDesc.setText( "תיאור:  " + item.getDesc());

            holder.itemImageView.setImageBitmap(ImageUtil.convertFrom64base(item.getImageRef()));
        }

        /// get the number of items in the list
        /// @return the number of items in the list
        @Override
        public int getItemCount() {
            return itemList.size();
        }

        /// View holder for the items adapter
        /// @see RecyclerView.ViewHolder
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
