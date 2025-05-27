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
import android.view.View;
import android.widget.Button;
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

    private ItemsAdapter itemsAdapter;
    private final List<Item> allItem = new ArrayList<>();

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
        mAuth= FirebaseAuth.getInstance();


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

    @Override
    public void onClick(View v) {

        Intent goReg=new Intent(ShowItems.this, ItemDetailActivity.class);

        startActivity(goReg);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuHomePageU) {
            Intent go = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(go);
        }
        else if (id == R.id.menuCartu) {
            Intent go = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(go);
        }
        else if (id == R.id.menuHPersonu) {
            Intent go = new Intent(getApplicationContext(), UpdateUserActivity.class);
            startActivity(go);
        }
        else if (id == R.id.menuLogOutu) {
            AuthenticationService.getInstance().signOut();
            Intent go = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(go);
        }
        return true;
    }

}