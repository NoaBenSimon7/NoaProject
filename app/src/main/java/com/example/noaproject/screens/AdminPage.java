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
import com.example.noaproject.models.User;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.utils.SharedPreferencesUtil;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {

    Button btnGoSearchPage,btnAddItem, btnGoAfterLoginM,btnGoAllOrders ;
    private User user;
    private String password;
    private String email;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);

        mAuth= FirebaseAuth.getInstance();
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
        mAuth= FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        if(v==btnGoSearchPage){
            Intent go=new Intent(this, SearchUsers.class);
            startActivity(go);
        }
        //  if(view==btnGoAddDonationPlace){
        //     Intent go=new Intent(this, AddDonationPlace.class);
        //      startActivity(go);
        // }
        if(v==btnGoAfterLoginM){
            Intent go=new Intent(this, ShowItems.class);
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










