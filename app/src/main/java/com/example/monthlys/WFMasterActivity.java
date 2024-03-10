package com.example.monthlys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WFMasterActivity extends AppCompatActivity {

    private RecyclerView recyclerViewExpenses;
    private Button buttonAddExpense;
    private WFExpenseAdapter adapter;
    private List<WFExpense> expenseList;
    private TextView textViewAmountRemaining;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wfmaster);

        // Initialize views
        recyclerViewExpenses = findViewById(R.id.recyclerViewExpenses);
        buttonAddExpense = findViewById(R.id.buttonAddExpense);
        textViewAmountRemaining = findViewById(R.id.textViewAmountRemaining);

        // Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewExpenses.setLayoutManager(layoutManager);

        // Initialize expenseList
        expenseList = new ArrayList<>();

        // Initialize adapter
        adapter = new WFExpenseAdapter(expenseList, this);
        recyclerViewExpenses.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Retrieve data from Firebase
        DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference().child("WFExpenses");
        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WFExpense expense = snapshot.getValue(WFExpense.class);
                    expenseList.add(expense);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        // Retrieve and display remaining budget amount
        retrieveAndDisplayBudgetAmount();

        // Set OnClickListener for the button to add a new expense
        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddWFExpenseActivity
                Intent intent = new Intent(WFMasterActivity.this, AddWFExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void retrieveAndDisplayBudgetAmount() {
        DatabaseReference budgetRef = FirebaseDatabase.getInstance().getReference().child("MWFBudget");
        DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference().child("WFExpenses");

        budgetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double budgetAmount = dataSnapshot.getValue(Double.class);

                    expensesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            double totalExpenses = 0.0;
                            for (DataSnapshot expenseSnapshot : dataSnapshot.getChildren()) {
                                WFExpense expense = expenseSnapshot.getValue(WFExpense.class);
                                totalExpenses += expense.getExpenseAmount();
                            }
                            double remainingAmount = budgetAmount - totalExpenses;
                            textViewAmountRemaining.setText(String.valueOf(remainingAmount) + " Rs.");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(WFMasterActivity.this, "Failed to retrieve expenses: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(WFMasterActivity.this, "Budget amount not found in Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(WFMasterActivity.this, "Failed to retrieve budget amount: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
