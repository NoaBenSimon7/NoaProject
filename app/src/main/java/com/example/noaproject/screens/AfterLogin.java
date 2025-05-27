package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class AfterLogin extends AppCompatActivity implements View.OnClickListener {


    TextView tvHello;
    Button btnGoStore2, btnGoAddItem2, btnGoWishList, btnGoPersonal, btnGoDonation;
    private FirebaseAuth mAuth;
    private User user;
    String email;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_after_login);


        btnGoStore2=findViewById(R.id.btnGoStore2);
        btnGoWishList=findViewById(R.id.btnGoMyCart);
        btnGoPersonal=findViewById(R.id.btnGoPersonal);

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
        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

        if(v==btnGoStore2){

            Intent go=new Intent(this, ShowItems.class);
            startActivity(go);
        }
        if(v==btnGoPersonal){
            Intent personal=new Intent(this, UpdateUserActivity.class);
            startActivity(personal);
        }
        if(v==btnGoWishList){
            Intent personal=new Intent(this, CartActivity.class);
            startActivity(personal);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        user= SharedPreferencesUtil.getUser(this);
        email=user.getEmail();
        password=user.getPassword();
        if (email.equals("admin@gmail.com") && password.equals("1111"))
        {
            getMenuInflater().inflate(R.menu.admin_menu,menu);
        }
        return true;
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