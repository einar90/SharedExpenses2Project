package com.einarbaumann.sharedexpenses2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewExpenseFragment extends Fragment  {

    public static Fragment newInstance(Context context) {
        NewExpenseFragment f = new NewExpenseFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.layout_new_expense, null);
        return root;
    }

}
