package com.example.noaproject.screens;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noaproject.R;
import com.example.noaproject.adapters.CartAdapter;
import com.example.noaproject.adapters.ItemsAdapter;
import com.example.noaproject.models.Cart;
import com.example.noaproject.models.Item;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "ShowcartActivity";

    private CartAdapter cartAdapter;
    private  Cart cart = null;

    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authenticationService= AuthenticationService.getInstance();
        uid=authenticationService.getCurrentUserId();

        // Initialize the database service
        databaseService = DatabaseService.getInstance();

        // Set up RecyclerView for displaying items
        RecyclerView selectedItemsRecyclerView = findViewById(R.id.rcCart);

        cartAdapter = new CartAdapter(cart);
        selectedItemsRecyclerView.setAdapter(cartAdapter);

        selectedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedItemsRecyclerView.setAdapter(cartAdapter);

        // Fetch all items from the database
        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart object) {
                cart=object;
                if(cart==null)
                    cart=new Cart();
                else{

                cartAdapter = new CartAdapter(cart);
                cartAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailed(Exception e) {
                cart=new Cart();

            }
        } );


        // Adapter for item spinner - This part is commented out, but you can add a spinner logic here
        // @see ItemSpinnerAdapter
    }
}