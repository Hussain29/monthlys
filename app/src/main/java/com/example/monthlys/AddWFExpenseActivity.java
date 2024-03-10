package com.example.monthlys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWFExpenseActivity extends AppCompatActivity {

    private EditText etExpenseDescription, etExpensePersons, etExpenseAmount, etExpenseDate;
    private Button btnAddExpense;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wfexpense);

        // Initialize views
        etExpenseDescription = findViewById(R.id.etExpenseDescription);
        etExpensePersons = findViewById(R.id.etExpensePersons);
        etExpenseAmount = findViewById(R.id.etExpenseAmount);
        etExpenseDate = findViewById(R.id.etExpenseDate);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        // Set OnClickListener for the Add Expense button
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input values from EditText fields
                String expenseDescription = etExpenseDescription.getText().toString().trim();
                String expensePersons = etExpensePersons.getText().toString().trim();
                String expenseAmountStr = etExpenseAmount.getText().toString().trim();
                String expenseDate = etExpenseDate.getText().toString().trim();

                // Check if any field is empty
                if (expenseDescription.isEmpty() || expensePersons.isEmpty() || expenseAmountStr.isEmpty() || expenseDate.isEmpty()) {
                    Toast.makeText(AddWFExpenseActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert expense amount to double
                double expenseAmount = Double.parseDouble(expenseAmountStr);

                // Get a reference to your Firebase Database
                DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference().child("WFExpenses");

                // Generate a unique key for the new expense
                String expenseId = expensesRef.push().getKey();

                // Create a new WFExpense object with the input values
                WFExpense expense = new WFExpense(expenseId, expenseDescription, expenseAmount, expenseDate, expensePersons);

                // Save the expense object to Firebase Database under "WFExpenses" node with the unique expenseId
                expensesRef.child(expenseId).setValue(expense)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Expense data successfully saved
                                Toast.makeText(AddWFExpenseActivity.this, "Expense Added Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save expense data
                                Toast.makeText(AddWFExpenseActivity.this, "Failed to add expense: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
