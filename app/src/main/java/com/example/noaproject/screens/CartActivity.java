package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private CartAdapter cartAdapter;
    private Cart cart = new Cart();
    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;
    private User user = null;

    private Button btnBackFcart,btnOrder;
    private FirebaseAuth mAuth;

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
        mAuth= FirebaseAuth.getInstance();

        authenticationService = AuthenticationService.getInstance();
        uid = authenticationService.getCurrentUserId();
        databaseService = DatabaseService.getInstance();


        // כפתור חזרה
        btnBackFcart = findViewById(R.id.btnBackFcart);
        btnBackFcart.setOnClickListener(this);
        btnOrder=findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(this);


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








    @Override
    public void onClick(View v) {
        if(v==btnBackFcart){
            Intent goLog = new Intent(CartActivity.this, AfterLogin.class);
            startActivity(goLog);
        }

        if(v==btnOrder){
            processOrder();

        }



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
        else if (id == R.id.menuHPersonu) {
            Intent go = new Intent(getApplicationContext(), UpdateUserActivity.class);
            startActivity(go);
        }
        else if (id == R.id.menuUserOrdersu) {
            Intent go = new Intent(getApplicationContext(), UserOrders.class);
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