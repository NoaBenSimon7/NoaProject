package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.utils.SharedPreferencesUtil;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnReg1, btnLog1, btnBio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
    }

    private void initViews() {
        btnLog1 = findViewById(R.id.btnLog);
        btnReg1=  findViewById(R.id.btnReg);

        btnReg1.setOnClickListener(this);
        btnLog1.setOnClickListener(this);

    }

    public void onClick(View v){

        if (v==btnReg1){
            Intent goReg=new Intent(MainActivity.this, Login.class);
            startActivity(goReg);
        }

        else if(v == btnLog1){
            Intent goLog=new Intent(MainActivity.this, Register.class);
            startActivity(goLog);
        }
        else if(v == btnBio){
            Intent goLog=new Intent(MainActivity.this, Bio.class);
            startActivity(goLog);
        }

    }


}