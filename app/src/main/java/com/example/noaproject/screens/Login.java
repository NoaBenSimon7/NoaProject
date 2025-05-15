package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.User;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.utils.SharedPreferencesUtil;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Login";
    EditText etEmail, etPassword;
    Button btnLog;
    String email, pass;
    AuthenticationService authenticationService;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();

        initViews();

        // Retrieve the user data from SharedPreferences
        user = SharedPreferencesUtil.getUser(this);
        if (user != null) {
            // Populate the email and password fields with the current user data
            etEmail.setText(user.getEmail());
            etPassword.setText(user.getPassword());
        }

    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmaillOG);
        etPassword = findViewById(R.id.etPasswordlOG);
        btnLog = findViewById(R.id.btnSubmitLog);
        btnLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        email = etEmail.getText().toString();
        pass = etPassword.getText().toString();

        // Check if the login is for the admin user
        if (email.equals("admin@gmail.com") && pass.equals("1111")) {
            // If it's the admin, navigate directly to AdminPage
            Intent intent = new Intent(Login.this, AdminPage.class);
            startActivity(intent);
            return;  // Don't proceed with Firebase login
        }

        // If it's not admin, proceed with Firebase login
        authenticationService.signIn(email, pass, new AuthenticationService.AuthCallback<String>() {
            @Override
            public void onCompleted(String id) {
                Log.d(TAG, "signInWithEmail: success");
                Intent go = new Intent(getApplicationContext(), AfterLogin.class);
                startActivity(go);
            }

            @Override
            public void onFailed(Exception e) {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail: failure", e);
                Toast.makeText(getApplicationContext(), "Authentication failed: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
