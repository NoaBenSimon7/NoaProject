package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.User;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.utils.SharedPreferencesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUsers extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> userList;
    private DatabaseReference usersRef;
    private SearchView searchView;
    private User user;
    private String email;
    private String password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        searchView = findViewById(R.id.SvSchoolComp);
        listView = findViewById(R.id.listView);

        // Create a list to hold user details
        userList = new ArrayList<>();

        // Initialize Firebase reference
        usersRef = FirebaseDatabase.getInstance().getReference("Users");  // Assuming "Users" is your node in Firebase

        // Initialize the adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);

        // Set the SearchView listener to filter the list based on query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Not needed for live filtering
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText); // Filter the list based on the text
                return false;
            }
        });
        mAuth= FirebaseAuth.getInstance();


        // Fetch users from Firebase
        fetchUsersFromFirebase();
    }
    private void fetchUsersFromFirebase() {
        // Get reference to the "Users" node
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();  // Clear the list before adding new data

                // Check if the snapshot is not null and contains children
                if (snapshot.exists()) {
                    // Loop through all the users in the snapshot
                    for (DataSnapshot data : snapshot.getChildren()) {
                        // Get the user object from the snapshot data
                        User user = data.getValue(User.class); // Ensure the User class has the correct structure

                        // If the user is not null, add to the list
                        if (user != null) {
                            // You can customize the detail format as needed
                            String userDetail = "👤 " + user.getFname() + " " + user.getLname() +
                                    "\n📧 Email: " + user.getEmail() +
                                    "\n📞 Phone: " + user.getPhone() ;

                            userList.add(userDetail);  // Add the formatted user details to the list
                        }
                    }

                    // Notify the adapter that the data has changed so the ListView can update
                    adapter.notifyDataSetChanged();

                    // If the list is empty after adding users, show a toast
                    if (userList.isEmpty()) {
                        Toast.makeText(SearchUsers.this, "No users found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If there are no users in the Firebase database
                    Toast.makeText(SearchUsers.this, "No users data available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the case when Firebase query is cancelled or fails
                Toast.makeText(SearchUsers.this, "Failed to load users. Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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