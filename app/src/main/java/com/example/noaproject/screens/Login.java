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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Login";
    EditText etEmail, etPassword;
    Button btnLog;
    String email, pass;
    AuthenticationService authenticationService;

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

        authenticationService = AuthenticationService.getInstance();

        initViews();

        // Optionally auto-fill email field (not password, for security)
        User savedUser = SharedPreferencesUtil.getUser(this);
        if (savedUser != null) {
            etEmail.setText(savedUser.getEmail());
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


        // Firebase login
        authenticationService.signIn(email, pass, new AuthenticationService.AuthCallback<String>() {


            @Override
            public void onCompleted(String userId) {
                Log.d(TAG, "signInWithEmail: success");
                if (email.equals("admin@gmail.com") && pass.equals("admin1111")) {
                    startActivity(new Intent(Login.this, AdminPage.class));

                }

                // Load full user from Firebase and save locally
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            SharedPreferencesUtil.saveUser(Login.this, user); // Admin login shortcut


                            Intent go = new Intent(Login.this, AfterLogin.class);
                            startActivity(go);
                        } else {
                            Toast.makeText(Login.this, "User not found in database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(Login.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                Log.w(TAG, "signInWithEmail: failure", e);
                Toast.makeText(getApplicationContext(), "Authentication failed: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
