package com.jabari.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {

    private Spinner itemSelectionSpinner;
    private EditText quantityInput, durationInput;
    private TextView transactionSummary, totalCostDisplay;
    private Button calculateButton, resetButton, goBackButton;

    // Dummy data for item prices
    private final Map<String, Double> itemPrices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        // Initialize item prices
        initializeItemPrices();

        // Initialize UI elements
        itemSelectionSpinner = findViewById(R.id.itemSelectionSpinner);
        quantityInput = findViewById(R.id.quantityInput);
        durationInput = findViewById(R.id.durationInput);
        transactionSummary = findViewById(R.id.transactionSummary);
        totalCostDisplay = findViewById(R.id.totalCostDisplay);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);
        goBackButton = findViewById(R.id.goBackButton);

        // Set up Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{
                "Mountain Village Retreat",
                "Coastal Serenity",
                "Cultural Heritage",
                "Architectural Grandeur",
                "River Gorge Adventure"
        });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSelectionSpinner.setAdapter(adapter);

        // Set up Calculate button
        calculateButton.setOnClickListener(v -> calculateTransaction());

        // Set up Reset button
        resetButton.setOnClickListener(v -> resetFields());

        // Set up Go Back button
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initializeItemPrices() {
        itemPrices.put("Mountain Village Retreat", 150.0);
        itemPrices.put("Coastal Serenity", 200.0);
        itemPrices.put("Cultural Heritage", 100.0);
        itemPrices.put("Architectural Grandeur", 180.0);
        itemPrices.put("River Gorge Adventure", 220.0);
    }

    private void calculateTransaction() {
        String selectedItem = itemSelectionSpinner.getSelectedItem().toString();
        String quantityStr = quantityInput.getText().toString();
        String durationStr = durationInput.getText().toString();

        if (quantityStr.isEmpty() || durationStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        int duration = Integer.parseInt(durationStr);

        double pricePerItem = itemPrices.getOrDefault(selectedItem, 0.0);
        double totalCost = quantity * duration * pricePerItem;

        transactionSummary.setText(String.format("Item: %s\nQuantity: %d\nDuration: %d days", selectedItem, quantity, duration));
        totalCostDisplay.setText(String.format("Total Cost: $%.2f", totalCost));
    }

    private void resetFields() {
        itemSelectionSpinner.setSelection(0);
        quantityInput.setText("");
        durationInput.setText("");
        transactionSummary.setText("");
        totalCostDisplay.setText("");
    }
}