package com.jabari.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewRecordsActivity extends AppCompatActivity {

    private static final String TAG = "ViewRecordsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        // Initialize Firestore and UI elements
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button goBackButton = findViewById(R.id.goBackButton);

        // Fetch records from Firestore and validate
        db.collection("records")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        // Process records here if needed
                        Log.d(TAG, "Records loaded successfully: " + queryDocumentSnapshots.size());
                        Toast.makeText(this, "Records loaded successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "No records found.");
                        Toast.makeText(this, "No records found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading records: ", e);
                    Toast.makeText(this, "Error loading records: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });

        // Go Back button functionality
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewRecordsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}