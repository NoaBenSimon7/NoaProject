package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;

public class AfterLogin extends AppCompatActivity implements View.OnClickListener {


    TextView tvHello;
    Button btnGoStore2, btnGoAddItem2, btnGoWishList, btnGoPersonal, btnGoDonation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_after_login);


        btnGoStore2=findViewById(R.id.btnGoStore2);
        // btnGoAddItem2=findViewById(R.id.btnGoAddItem2);
        btnGoWishList=findViewById(R.id.btnGoMyCart);
        btnGoPersonal=findViewById(R.id.btnGoPersonalArea);

        //   if(Login.theUser != null)
        //     tvHello.setText(" שלום "+Login.theUser.getfName());

        btnGoStore2.setOnClickListener(this);

        btnGoWishList.setOnClickListener(this);
        btnGoPersonal.setOnClickListener(this);

        startService(new Intent(this, MyService.class));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {

        if(v==btnGoStore2){

            Intent go=new Intent(this, ShowItems.class);
            startActivity(go);
        }


    }







}