package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.noaproject.R;
import com.example.noaproject.adapters.OrderAdapter;
import com.example.noaproject.models.Order;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class UserOrders extends AppCompatActivity {
    RecyclerView rcAllordera;
    ArrayList<Order> orders=new ArrayList<>();
    OrderAdapter orderAdapter;

    DatabaseService databaseService;

    String uid;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();


        uid= AuthenticationService.getInstance().getCurrentUserId();


        databaseService=DatabaseService.getInstance();
        rcAllordera=findViewById(R.id.rcUserorders);

        rcAllordera.setLayoutManager(new LinearLayoutManager(this));

        rcAllordera.setFadingEdgeLength(50);

        orderAdapter=new OrderAdapter(UserOrders.this,orders);
        rcAllordera.setAdapter(orderAdapter);
        databaseService.getUserOrders(uid, new DatabaseService.DatabaseCallback<List<Order>>() {
            @Override
            public void onCompleted(List<Order> object) {
                orders.clear();
                for (Order order : object) {
                    if (order.getUser() != null && order.getUser().getId() != null && order.getUser().getId().equals(uid)) {
                        orders.add(order);
                    }
                }
                orderAdapter.notifyDataSetChanged();
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
        else if (id == R.id.menuCartu) {
            Intent go = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(go);
        }
        else if (id == R.id.menuHPersonu) {
            AuthenticationService.getInstance().signOut();
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
