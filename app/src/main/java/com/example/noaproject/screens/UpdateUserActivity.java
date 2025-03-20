package com.example.noaproject.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.noaproject.R;
import com.example.noaproject.models.User;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText editFname, editLname, editEmail, editPhone, editPassword;
    private Button btnUpdate;
    private User currentUser; // Assume this user is fetched from the database or session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Initialize views
        editFname = findViewById(R.id.editFname);
        editLname = findViewById(R.id.editLname);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Assuming currentUser is the user fetched from a database or session
        currentUser = new User("1", "John", "Doe", "john.doe@example.com", "1234567890", "password123");

        // Populate the fields with the current user's details
        editFname.setText(currentUser.getFname());
        editLname.setText(currentUser.getLname());
        editEmail.setText(currentUser.getEmail());
        editPhone.setText(currentUser.getPhone());
        editPassword.setText(currentUser.getPassword());

        // Set up the update button click listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated details from the EditTexts
                String updatedFname = editFname.getText().toString();
                String updatedLname = editLname.getText().toString();
                String updatedEmail = editEmail.getText().toString();
                String updatedPhone = editPhone.getText().toString();
                String updatedPassword = editPassword.getText().toString();

                // Validate input
                if (updatedFname.isEmpty() || updatedLname.isEmpty() || updatedEmail.isEmpty() || updatedPhone.isEmpty() || updatedPassword.isEmpty()) {
                    Toast.makeText(UpdateUserActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the currentUser with the new values
                currentUser.setFname(updatedFname);
                currentUser.setLname(updatedLname);
                currentUser.setEmail(updatedEmail);
                currentUser.setPhone(updatedPhone);
                currentUser.setPassword(updatedPassword);

                // Call method to save the updated user (e.g., save to database, shared preferences, etc.)
                updateUserDetails(currentUser);

                // Show a success message
                Toast.makeText(UpdateUserActivity.this, "User details updated successfully!", Toast.LENGTH_SHORT).show();

                // Optionally finish the activity to go back to the previous screen
                finish();
            }
        });
    }

    private void updateUserDetails(User updatedUser) {
        // Implement the logic to save the updated user details (e.g., save to database, shared preferences, etc.)
        // For example, if you're using shared preferences:
        // SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        // SharedPreferences.Editor editor = sharedPreferences.edit();
        // editor.putString("user_id", updatedUser.getId());
        // editor.putString("user_fname", updatedUser.getFname());
        // editor.putString("user_lname", updatedUser.getLname());
        // editor.putString("user_email", updatedUser.getEmail());
        // editor.putString("user_phone", updatedUser.getPhone());
        // editor.putString("user_password", updatedUser.getPassword());
        // editor.apply();
    }
}