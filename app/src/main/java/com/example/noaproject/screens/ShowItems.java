package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.adapters.ItemsAdapter;
import com.example.noaproject.models.Item;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity implements View.OnClickListener {
    /// tag for logging
    private static final String TAG = "ShowItemsActivity";

    private Spinner itemSpinner;
    private Button addButton, createCartButton;
    private SearchView searchView;

    private ItemsAdapter itemsAdapter;
    private final List<Item> allItem = new ArrayList<>();
    private final List<Item> filteredItems = new ArrayList<>();

    private DatabaseService databaseService;
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();

        // Initialize the database service
        databaseService = DatabaseService.getInstance();

        // Initialize SearchView from layout
        searchView = findViewById(R.id.svItems);
        setupSearchView();

        // Set up RecyclerView for displaying items
        RecyclerView selectedItemsRecyclerView = findViewById(R.id.rcItems);

        // Use filteredItems for the adapter instead of allItem
        itemsAdapter = new ItemsAdapter(filteredItems);

        selectedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedItemsRecyclerView.setAdapter(itemsAdapter);

        // Fetch all items from the database
        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> object) {
                Log.d(TAG, "onCompleted: " + object);
                allItem.clear();
                allItem.addAll(object);

                // Initially show all items
                filteredItems.clear();
                filteredItems.addAll(allItem);

                // Notify the adapter that data has changed
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: ", e);
            }
        });
    }

    private void setupSearchView() {
        if (searchView != null) {
            searchView.setQueryHint("חפש מוצרים...");
            searchView.setMaxWidth(Integer.MAX_VALUE);

            // Set up search listener
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    filterItems(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterItems(newText);
                    return true;
                }
            });

            // Handle search view close
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    // Show all items when search is closed
                    filteredItems.clear();
                    filteredItems.addAll(allItem);
                    itemsAdapter.notifyDataSetChanged();
                    return false;
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Intent goReg = new Intent(ShowItems.this, ItemDetailActivity.class);
        startActivity(goReg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void filterItems(String query) {
        filteredItems.clear();

        if (query == null || query.trim().isEmpty()) {
            // If query is empty, show all items
            filteredItems.addAll(allItem);
        } else {
            // Filter items based on the query
            String lowerCaseQuery = query.toLowerCase().trim();

            for (Item item : allItem) {
                // Using getItemName() based on your adapter
                if (item.getItemName() != null && item.getItemName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredItems.add(item);
                }
                // You can add more search criteria here, for example:
                //else if (item.getPrice() != null && item.getPrice().toLowerCase().contains(lowerCaseQuery)) {
                    //filteredItems.add(item);
               // }
                // Add more fields as needed based on your Item model
            }
        }

        // Notify adapter of data change
        itemsAdapter.notifyDataSetChanged();

        // Show a message if no items found
        if (filteredItems.isEmpty() && !query.trim().isEmpty()) {
            Toast.makeText(this, "לא נמצאו מוצרים עבור: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuHomePageU) {
            Intent go = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(go);
        } else if (id == R.id.menuCartu) {
            Intent go = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(go);
        } else if (id == R.id.menuHPersonu) {
            Intent go = new Intent(getApplicationContext(), UpdateUserActivity.class);
            startActivity(go);
        } else if (id == R.id.menuLogOutu) {
            AuthenticationService.getInstance().signOut();
            Intent go = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(go);
        }
        return true;
    }
}