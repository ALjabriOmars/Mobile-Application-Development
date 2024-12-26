package com.jabari.mytravelapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteRecordActivity extends AppCompatActivity {

    private EditText recordIdEditText;
    private Button deleteButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_record);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        recordIdEditText = findViewById(R.id.recordIdEditText);
        deleteButton = findViewById(R.id.deleteButton);

        // Delete button click listener
        deleteButton.setOnClickListener(v -> {
            String recordId = recordIdEditText.getText().toString().trim();

            if (recordId.isEmpty()) {
                Toast.makeText(DeleteRecordActivity.this, "Please enter a record ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Delete record from Firestore
            db.collection("records").document(recordId)
                    .delete()
                    .addOnSuccessListener(aVoid -> Toast.makeText(DeleteRecordActivity.this, "Record deleted successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(DeleteRecordActivity.this, "Error deleting record: " + e.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}