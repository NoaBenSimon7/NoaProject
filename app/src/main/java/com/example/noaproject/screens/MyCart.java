package com.example.noaproject.screens;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyCart extends AppCompatActivity {



    ListView lvInfo;
    ItemOrderAdapter adp;
    private FirebaseDatabase database;
    private DatabaseReference myRef, orderRef;
    private FirebaseAuth auth;

    private Cart value;
    private Cart theCart=new Cart();

    Order theOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_cart);

        database = FirebaseDatabase.getInstance();
        lvInfo = findViewById(R.id.lvCartInfo);
        auth = FirebaseAuth.getInstance();


        database = FirebaseDatabase.getInstance();
        orderRef = database.getReference("Orders").push();

        myRef = database.getReference("Users").child(auth.getUid()).child("cart");


    }}