package com.example.noaproject.screens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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

import java.util.ArrayList;
import java.util.List;

public class UserOrders extends AppCompatActivity {
    RecyclerView rcAllordera;
    ArrayList<Order> orders=new ArrayList<>();
    OrderAdapter orderAdapter;

    DatabaseService databaseService;

    String uid;


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
                orders.addAll(object);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    }
