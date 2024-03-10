package com.example.monthlys;

import android.os.Parcel;
import android.os.Parcelable;

public class WFExpense implements Parcelable {
    private String expenseId;
    private String expenseDescription;
    private double expenseAmount;
    private String expenseDate;
    private String persons;

    // Constructors
    public WFExpense() {
        // Default constructor required for calls to DataSnapshot.getValue(WFExpense.class)
    }

    public WFExpense(String expenseId, String expenseDescription, double expenseAmount, String expenseDate, String persons) {
        this.expenseId = expenseId;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.persons = persons;
    }

    // Getters and Setters
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

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    // Parcelable Implementation
    protected WFExpense(Parcel in) {
        expenseId = in.readString();
        expenseDescription = in.readString();
        expenseAmount = in.readDouble();
        expenseDate = in.readString();
        persons = in.readString();
    }

    public static final Creator<WFExpense> CREATOR = new Creator<WFExpense>() {
        @Override
        public WFExpense createFromParcel(Parcel in) {
            return new WFExpense(in);
        }

        @Override
        public WFExpense[] newArray(int size) {
            return new WFExpense[size];
        }
    };

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
        dest.writeString(persons);
    }
}
