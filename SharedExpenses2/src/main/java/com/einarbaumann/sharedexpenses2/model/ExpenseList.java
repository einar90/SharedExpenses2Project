package com.einarbaumann.sharedexpenses2.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Einar on 12.07.13.
 */
public class ExpenseList {

    private static ExpenseList instance = new ExpenseList();
    private ArrayList<Expense> _expenseList = new ArrayList<Expense>();

    private ExpenseList() {}

    public static ExpenseList getInstance() {
        return instance;
    }

    public void addExpense(Expense expense) {
        _expenseList.add(expense);
        Log.d("Adding", "Added expense to list");
        Log.d("Adding", "Current expenses: " + _expenseList.toString());
    }

    public ArrayList<Expense> getList(){
        return _expenseList;
    }

}
