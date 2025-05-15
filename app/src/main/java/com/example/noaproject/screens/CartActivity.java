package com.example.noaproject.screens;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noaproject.R;
import com.example.noaproject.adapters.CartAdapter;
import com.example.noaproject.models.Cart;
import com.example.noaproject.models.Order;
import com.example.noaproject.models.User;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.services.DatabaseService;

public class CartActivity extends AppCompatActivity {

    private CartAdapter cartAdapter;
    private Cart cart = new Cart();
    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;
    private User user = null;

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

        authenticationService = AuthenticationService.getInstance();
        uid = authenticationService.getCurrentUserId();
        databaseService = DatabaseService.getInstance();

        RecyclerView selectedItemsRecyclerView = findViewById(R.id.rcCart);
        cartAdapter = new CartAdapter(CartActivity.this, cart);
        selectedItemsRecyclerView.setAdapter(cartAdapter);
        selectedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart object) {
                cart = object != null ? object : new Cart();
                cartAdapter.setCart(cart);
            }

            @Override
            public void onFailed(Exception e) {
                cart = new Cart();
                cartAdapter.setCart(cart);
            }
        });

        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User object) {
                user = new User(object);
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }

    private void processOrder() {
        if (cart == null || cart.getItems().isEmpty()) {
            Toast.makeText(this, "העגלה ריקה!", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderId = databaseService.generateOrderId();
        Order order = new Order(orderId, cart.getItems(), cart.getTotalCart(), "new", user, 0);
        order.setTimestamp(System.currentTimeMillis());

        databaseService.createNewOreder(order, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Toast.makeText(CartActivity.this, "הזמנה נשמרה!", Toast.LENGTH_SHORT).show();
                cart = new Cart();
                goUpdateCart(cart);
                cartAdapter.setCart(cart);
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(CartActivity.this, "שגיאה בשמירת ההזמנה", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goUpdateCart(Cart cart) {
        databaseService.updateCart(cart, uid, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }
}