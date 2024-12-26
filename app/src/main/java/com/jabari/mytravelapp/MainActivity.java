package com.jabari.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView profileNameTextView;
    private EditText searchBar;
    private Button addButton, updateButton, deleteButton, viewButton, transactionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        profileNameTextView = findViewById(R.id.profileName);
        searchBar = findViewById(R.id.searchBar);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        viewButton = findViewById(R.id.viewButton);
        transactionButton = findViewById(R.id.transactionButton);

        // Set up banners
        ImageView banner1 = findViewById(R.id.banner1);
        ImageView banner2 = findViewById(R.id.banner2);

        // Load user information
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String profileName = user.getDisplayName();
            if (profileName == null || profileName.isEmpty()) {
                profileName = user.getEmail(); // Fallback to email
            }
            profileNameTextView.setText("Welcome, " + profileName);
        } else {
            profileNameTextView.setText("Welcome, Admin");
        }

        // Set up button click listeners
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
            startActivity(intent);
        });

        updateButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Item list refreshed!", Toast.LENGTH_SHORT).show();
        });

        deleteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DeleteRecordActivity.class);
            startActivity(intent);
        });

        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewRecordsActivity.class);
            startActivity(intent);
        });

        transactionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
        });

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ViewRecordsActivity.class);
                intent.putExtra("searchQuery", query);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }
}