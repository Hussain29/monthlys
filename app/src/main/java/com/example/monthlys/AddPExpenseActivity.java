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


public class AddPExpenseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText etExpenseDescription, etExpenseAmount, etExpenseDate;
    private Button btnAddExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_expense);

        // Initialize views
        etExpenseDescription = findViewById(R.id.etExpenseDescription);
        etExpenseAmount = findViewById(R.id.etExpenseAmount);
        etExpenseDate = findViewById(R.id.etExpenseDate);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Expense...");
        progressDialog.setCancelable(false); // Prevent user from dismissing dialog by tapping outside

        // Set OnClickListener for the Add Expense button
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show ProgressDialog
                progressDialog.show();

                // Retrieve input values from EditText fields
                String expenseDescription = etExpenseDescription.getText().toString().trim();
                String expenseAmountStr = etExpenseAmount.getText().toString().trim();
                String expenseDate = etExpenseDate.getText().toString().trim();

                // Check if any field is empty
                if (expenseDescription.isEmpty() || expenseAmountStr.isEmpty() || expenseDate.isEmpty()) {
                    Toast.makeText(AddPExpenseActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss(); // Dismiss ProgressDialog
                    return;
                }

                // Convert expense amount to double
                double expenseAmount = Double.parseDouble(expenseAmountStr);

                // Get a reference to your Firebase Database
                DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference().child("PExpenses");

                // Generate a unique key for the new expense
                String expenseId = expensesRef.push().getKey();

                // Create a new Expense object with the input values
                PExpense expense = new PExpense(expenseId, expenseDescription, expenseAmount, expenseDate);

                // Save the expense object to Firebase Database under "expenses" node with the unique expenseId
                expensesRef.child(expenseId).setValue(expense)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Expense data successfully saved
                                Toast.makeText(AddPExpenseActivity.this, "Expense Added Successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss(); // Dismiss ProgressDialog
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save expense data
                                Toast.makeText(AddPExpenseActivity.this, "Failed to add expense: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss(); // Dismiss ProgressDialog
                            }
                        });
            }
        });
    }
}
