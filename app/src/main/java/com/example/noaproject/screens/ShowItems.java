package com.example.noaproject.screens;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity {
    /// tag for logging
    private static final String TAG = "AddCartActivity";

    private Spinner itemSpinner;
    private Button addButton, createCartButton;

    private ItemsAdapter itemsAdapter;
    private List<Item> allItem=new ArrayList<>();

    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();

        /// get the views



        /// Adapter for the item recycler view
        /// @see ArrayAdapter
        /// @see Item
        RecyclerView selectedItemsRecyclerView = findViewById(R.id.rcItems);

        itemsAdapter = new ItemsAdapter(allItem);
        selectedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedItemsRecyclerView.setAdapter(itemsAdapter);

        /// Adapter for the item spinner
        /// @see ItemSpinnerAdapter
        /// @see Item




        /// get all the items from the database

        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {



            @Override
            public void onCompleted(List<Item> object) {
                Log.d(TAG, "onCompleted: " + object);
                allItem.clear();
                allItem.addAll(object);
                /// notify the adapter that the data has changed
                /// this specifies that the data has changed
                /// and the adapter should update the view
                /// @see ItemSpinnerAdapter#notifyDataSetChanged()
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: ", e);
            }
        });



    }


}
