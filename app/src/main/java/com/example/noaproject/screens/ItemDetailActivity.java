package com.example.noaproject.screens;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.Item;
import com.example.noaproject.utils.ImageUtil;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView itemName, itemType, itemSize, itemColor, itemFabric, itemDesc, itemPrice;
    private ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);



        // Initialize views
        itemName = findViewById(R.id.item_name);
        itemType = findViewById(R.id.item_type);
        itemSize = findViewById(R.id.item_size);
        itemColor = findViewById(R.id.item_color);
        itemFabric = findViewById(R.id.item_fabric);
        itemDesc = findViewById(R.id.item_desc);
        itemPrice = findViewById(R.id.item_price);
        itemImage = findViewById(R.id.item_image);

        // Get the item passed from the intent
        Item item = (Item) getIntent().getSerializableExtra("item");

        if (item != null) {
            itemName.setText(item.getItemName());
            itemType.setText(item.getType());
            itemSize.setText(item.getSize());
            itemColor.setText(item.getColor());
            itemFabric.setText(item.getFabric());
            itemDesc.setText(item.getDesc());
            itemPrice.setText(String.valueOf(item.getPrice())+"");

        itemImage.setImageBitmap(ImageUtil.convertFrom64base(item.getImageRef()));
//
            // Load image (you can use a library like Glide or Picasso to load images)
            // Glide.with(this).load(item.getImageRef()).into(itemImage);
        }
    }
}



