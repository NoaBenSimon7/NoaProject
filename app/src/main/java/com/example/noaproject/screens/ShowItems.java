package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.adapters.ItemsAdapter;
import com.example.noaproject.models.Item;
import com.example.noaproject.services.DatabaseService;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity {
    /// tag for logging
    private static final String TAG = "ShowItemsActivity";

    private Spinner itemSpinner;
    private Button addButton, createCartButton;

    private ItemsAdapter itemsAdapter;
    private final List<Item> allItem = new ArrayList<>();

    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_items);

        // Adjust layout for system bars (status bar, navigation bar, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the database service
        databaseService = DatabaseService.getInstance();

        // Set up RecyclerView for displaying items
        RecyclerView selectedItemsRecyclerView = findViewById(R.id.rcItems);

        itemsAdapter = new ItemsAdapter(allItem);
        selectedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedItemsRecyclerView.setAdapter(itemsAdapter);

        // Fetch all items from the database
        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> object) {
                Log.d(TAG, "onCompleted: " + object);
                allItem.clear();
                allItem.addAll(object);
                // Notify the adapter that data has changed
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: ", e);
            }
        });

        // Adapter for item spinner - This part is commented out, but you can add a spinner logic here
        // @see ItemSpinnerAdapter
    }

    // Optionally, you can handle the item click directly in this activity,
    // but since it's done in the adapter itself, you don't need to add click handling here.
}
