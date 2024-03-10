package com.example.monthlys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WFExpenseAdapter extends RecyclerView.Adapter<WFExpenseAdapter.ViewHolder> {

    private List<WFExpense> expenseList;
    private Context context;

    public WFExpenseAdapter(List<WFExpense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wfexpense_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WFExpense expense = expenseList.get(position);

        // Set the data to the views
        holder.tvExpenseDescription.setText(expense.getExpenseDescription());
        holder.tvExpenseAmount.setText(String.valueOf(expense.getExpenseAmount()));
        holder.tvExpenseDate.setText(expense.getExpenseDate());
        holder.tvPersons.setText(expense.getPersons());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpenseDescription, tvExpenseAmount, tvExpenseDate, tvPersons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpenseDescription = itemView.findViewById(R.id.tvExpenseDescription);
            tvExpenseAmount = itemView.findViewById(R.id.tvExpenseAmount);
            tvExpenseDate = itemView.findViewById(R.id.tvExpenseDate);
            tvPersons = itemView.findViewById(R.id.tvExpensePersons);
        }
    }
}
