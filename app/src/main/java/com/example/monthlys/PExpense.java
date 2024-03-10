package com.example.monthlys;

import android.os.Parcel;
import android.os.Parcelable;

public class PExpense implements Parcelable {

    private String expenseId;
    private String expenseDescription;
    private double expenseAmount;
    private String expenseDate;

    public PExpense() {
        // Default constructor required for calls to DataSnapshot.getValue(Expense.class)
    }

    public PExpense(String expenseId, String expenseDescription, double expenseAmount, String expenseDate) {
        this.expenseId = expenseId;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
    }

    protected PExpense(Parcel in) {
        expenseId = in.readString();
        expenseDescription = in.readString();
        expenseAmount = in.readDouble();
        expenseDate = in.readString();
    }

    public static final Creator<PExpense> CREATOR = new Creator<PExpense>() {
        @Override
        public PExpense createFromParcel(Parcel in) {
            return new PExpense(in);
        }

        @Override
        public PExpense[] newArray(int size) {
            return new PExpense[size];
        }
    };

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expenseId);
        dest.writeString(expenseDescription);
        dest.writeDouble(expenseAmount);
        dest.writeString(expenseDate);
    }
}
