package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {

    Button btnGoSearchPage,btnAddItem, btnGoAfterLoginM,btnGoAllOrders ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);


        btnGoSearchPage=findViewById(R.id.btnGoSearchPage);
        //    btnGoAddDonationPlace=findViewById(R.id.btnGoAddDonationPlace);
        btnGoAfterLoginM=findViewById(R.id.btnGoAfterLoginM);

        btnAddItem=findViewById(R.id.btnAddItem);
        btnGoAllOrders=findViewById(R.id.btnGoAllOrders);

        btnGoSearchPage.setOnClickListener(this);

        btnGoAfterLoginM.setOnClickListener(this);
        btnGoAllOrders.setOnClickListener(this);

        btnAddItem.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        if(v==btnGoSearchPage){
            Intent go=new Intent(this, SearchPage.class);
            startActivity(go);
        }
        //  if(view==btnGoAddDonationPlace){
        //     Intent go=new Intent(this, AddDonationPlace.class);
        //      startActivity(go);
        // }
        if(v==btnGoAfterLoginM){
            Intent go=new Intent(this, AfterLogin.class);
            startActivity(go);
        }

        if(v==btnAddItem){
            Intent go=new Intent(this,AddItem.class);
            startActivity(go);
        }
        if(v==btnGoAllOrders){
            Intent go=new Intent(this,AllOrders.class);
            startActivity(go);
        }
    }








}










}
}