package com.example.noaproject.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.noaproject.R;
import com.example.noaproject.models.Item;
import com.example.noaproject.models.User;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.services.DatabaseService;
import com.example.noaproject.utils.SharedPreferencesUtil;

import java.util.List;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText editFname, editLname, editPhone;
    private Button btnUpdate;
    private User currentUser=null; // Assume this user is fetched from the database or session
    private AuthenticationService authenticationService;

    DatabaseService databaseService;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        // get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        databaseService=DatabaseService.getInstance();
        uid=authenticationService.getCurrentUserId();



        // Retrieve the user data from SharedPreferences


     databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User object) {

                currentUser=object;

                editFname.setText(currentUser.getFname());
                editLname.setText(currentUser.getLname());
               // editEmail.setText(currentUser.getEmail());
                editPhone.setText(currentUser.getPhone());


            }

            @Override
            public void onFailed(Exception e) {

            }
        });





        // Initialize views
        editFname = findViewById(R.id.editFname);
        editLname = findViewById(R.id.editLname);

        editPhone = findViewById(R.id.editPhone);

        btnUpdate = findViewById(R.id.btnUpdate);




        // Set up the update button click listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated details from the EditTexts
                String updatedFname = editFname.getText().toString();
                String updatedLname = editLname.getText().toString();

                String updatedPhone = editPhone.getText().toString();


                // Validate input
                if (updatedFname.isEmpty() || updatedLname.isEmpty()  || updatedPhone.isEmpty() ) {
                    Toast.makeText(UpdateUserActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the currentUser with the new values
                currentUser.setFname(updatedFname);
                currentUser.setLname(updatedLname);

                currentUser.setPhone(updatedPhone);


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
        databaseService.createNewUser(updatedUser, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {

            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }
}