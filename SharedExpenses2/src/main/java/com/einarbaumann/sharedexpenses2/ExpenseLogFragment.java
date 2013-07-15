package com.einarbaumann.sharedexpenses2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExpenseLogFragment extends Fragment {

    public static Fragment newInstance(Context context) {
        ExpenseLogFragment f = new ExpenseLogFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.layout_expense_log, null);
        return root;
    }

}