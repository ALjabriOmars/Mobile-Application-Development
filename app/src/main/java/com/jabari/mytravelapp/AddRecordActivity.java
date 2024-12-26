package com.jabari.mytravelapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AddRecordActivity extends AppCompatActivity {

    private EditText itemNameEditText, itemDescriptionEditText;
    private Button uploadImageButton, saveButton;
    private Uri selectedImageUri;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Initialize UI elements
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemDescriptionEditText = findViewById(R.id.itemDescriptionEditText);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        saveButton = findViewById(R.id.saveButton);

        // Set up upload image button
        uploadImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            imagePickerLauncher.launch(Intent.createChooser(intent, "Select Image"));
        });

        // Set up save button
        saveButton.setOnClickListener(v -> {
            String name = itemNameEditText.getText().toString().trim();
            String description = itemDescriptionEditText.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || selectedImageUri == null) {
                Toast.makeText(AddRecordActivity.this, "Please fill all fields and upload an image", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save image to Firebase Storage
            StorageReference imageRef = storageReference.child("images/" + System.currentTimeMillis() + ".jpg");
            imageRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot ->
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Save record to Firestore
                        Map<String, Object> record = new HashMap<>();
                        record.put("name", name);
                        record.put("description", description);
                        record.put("imageUrl", uri.toString());

                        db.collection("records").add(record)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(AddRecordActivity.this, "Record added successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(AddRecordActivity.this, "Failed to add record: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    })
            ).addOnFailureListener(e -> Toast.makeText(AddRecordActivity.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}