package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

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
import com.example.noaproject.models.User;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.services.DatabaseService;
import com.example.noaproject.utils.SharedPreferencesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AllOrders extends AppCompatActivity {



    RecyclerView rcAllordera;
    ArrayList<Order> orders=new ArrayList<>();
    OrderAdapter orderAdapter;

    DatabaseService databaseService;
    private FirebaseAuth mAuth;
    private User user;
    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_orders);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth= FirebaseAuth.getInstance();

        databaseService=DatabaseService.getInstance();
        rcAllordera=findViewById(R.id.rcAllorders);

        rcAllordera.setLayoutManager(new LinearLayoutManager(this));

        rcAllordera.setFadingEdgeLength(50);

        orderAdapter=new OrderAdapter(AllOrders.this,orders);
        rcAllordera.setAdapter(orderAdapter);
        databaseService.getAllOrders(new DatabaseService.DatabaseCallback<List<Order>>() {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);;
        user= SharedPreferencesUtil.getUser(this);
        email=user.getEmail();
        password=user.getPassword();

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuHomePage) {
            Intent go = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(go);
        }
        else if (id == R.id.menuLogOut) {
            AuthenticationService.getInstance().signOut();
            Intent go = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(go);
        }
        else if (id == R.id.menuSearchUser) {
            Intent go = new Intent(getApplicationContext(), SearchUsers.class);
            startActivity(go);
        }
        else if (id == R.id.menuAddProduct) {
            Intent go = new Intent(getApplicationContext(), AddItem.class);
            startActivity(go);
        }
        else if (id == R.id.menuAllOrders) {
            Intent go = new Intent(getApplicationContext(), AllOrders.class);
            startActivity(go);
        }
        return true;
    }
}

