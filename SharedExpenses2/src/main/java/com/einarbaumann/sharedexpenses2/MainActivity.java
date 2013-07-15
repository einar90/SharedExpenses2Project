package com.einarbaumann.sharedexpenses2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.einarbaumann.sharedexpenses2.model.Expense;
import com.einarbaumann.sharedexpenses2.model.ExpenseList;
import com.einarbaumann.sharedexpenses2.model.Names;
import com.einarbaumann.sharedexpenses2.model.Person;

public class MainActivity extends FragmentActivity {

    private ViewPager _mViewPager;
    private ViewPagerAdapter _adapter;

    // State variables
    private boolean personOneChecked = false;
    private boolean personTwoChecked = false;

    // Log related objects
    private static ListView expenseLogListView;
    private static ExpenseLogItemAdapter expenseLogItemAdapter;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
        setTab();
    }
    private void setUpView(){
        _mViewPager = (ViewPager) findViewById(R.id.viewPager);
        _adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);
    }
    private void setTab(){
        _mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        findViewById(R.id.first_tab).setVisibility(View.VISIBLE);
                        findViewById(R.id.second_tab).setVisibility(View.INVISIBLE);
                        break;

                    case 1:
                        findViewById(R.id.first_tab).setVisibility(View.INVISIBLE);
                        findViewById(R.id.second_tab).setVisibility(View.VISIBLE);
                        break;
                }
            }

        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        // Checking if this is the first startup
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        boolean firstStart = sharedPref.getBoolean("firstStart", true);

        // Running initial setup if firstStart == true
        if (firstStart) {
            initialSetup();
        }

        // Initializing expense log if it is not empty
        if (ExpenseList.getInstance().getList().size() > 0) {
            initializeExpenseLog();
        }
    }

    private void initializeExpenseLog() {
        expenseLogListView = (ListView) findViewById(R.id.logView);
        expenseLogItemAdapter = new ExpenseLogItemAdapter(this, android.R.layout.simple_list_item_1, ExpenseList.getInstance().getList());
        expenseLogListView.setAdapter(expenseLogItemAdapter);
        /*
        expenseLogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showEditExpenseDialog(position);
            }
        });
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Setting initial data
     */
    public void initialSetup() {
        Intent intent = new Intent(this, InitConfigActivity.class);
        startActivity(intent);
    }



    /**
     * Executed when the "Save" button on the "New" tab activity is tapped.
     * @param view
     */
    public void Click_SaveExpense(View view) {
        Log.d("Clicked", "Save button clicked");

        // Getting text fields
        EditText amountET = (EditText) findViewById(R.id.TF_amount);
        EditText descriptionET = (EditText) findViewById(R.id.TF_description);

        // Getting text field contents
        double amount = 0;
        String description = "";
        try {
            Log.d("Adding", "Amountstring: " + amountET.getText());
            amount = Double.parseDouble(amountET.getText().toString());
            description = descriptionET.getText().toString();
        } catch (NumberFormatException e) {
            Log.d("Adding", "Number format exception");
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Could not interpret amount.");
            alertDialog.show();
            return;
        }

        // Setting paidBy variable
        Person paidBy;
        if (personOneChecked) {
            paidBy = Person.ONE;
        }
        else paidBy = Person.TWO;

        // Creating new Expense object and adding to ExpenseList
        Expense expense = new Expense(paidBy, amount, description);
        ExpenseList.getInstance().addExpense(expense);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Successfully added expense");
        alertDialog.setMessage(amount + " paid by " + Names.getInstance().getName(paidBy));
        alertDialog.show();

        clearSchema();
        initializeExpenseLog();

    }

    /**
     * Executed when the "Cancel" button on the "New" tab i tapped.
     * @param view
     */
    public void Click_CancelExpense(View view) {
        Log.d("Clicked", "Cancel button clicked");
        clearSchema();
    }

    /**
     * Executed when the "PersonTwo" frame on the "New" tab i tapped.
     * @param view
     */
    public void Click_PersonOneCheck(View view) {

        personOneChecked = true;
        personTwoChecked = false;
        Button personOneButton = (Button) findViewById(R.id.btn_person_one);
        Button personTwoButton = (Button) findViewById(R.id.btn_person_two);
        personChecked(personOneButton);
        personUnchecked(personTwoButton);
        Log.d("Clicked", "Person one checked");
    }

    /**
     * Executed when the "PersonOne" frame on the "New" tab i tapped.
     * @param view
     */
    public void Click_PersonTwoCheck(View view) {
        personTwoChecked = true; personOneChecked = false;
        Button personOneButton = (Button) findViewById(R.id.btn_person_one);
        Button personTwoButton = (Button) findViewById(R.id.btn_person_two);
        personChecked(personTwoButton);
        personUnchecked(personOneButton);
        Log.d("Clicked", "Person two checked");
    }

    private void personChecked(Button button) {
        button.setBackgroundResource(R.drawable.checked_background_box);
        button.setTextColor(Color.WHITE);
    }

    private void personUnchecked(Button button) {
        button.setBackgroundResource(R.drawable.unchecked_background_box);
        button.setTextColor(Color.rgb(115, 115, 115));
    }

    private void clearSchema() {
        // Getting things to reset
        EditText amountField = (EditText) findViewById(R.id.TF_amount);
        EditText descField = (EditText) findViewById(R.id.TF_description);
        Button personOneButton = (Button) findViewById(R.id.btn_person_one);
        Button personTwoButton = (Button) findViewById(R.id.btn_person_two);

        // Resetting text fields
        amountField.setText("");
        descField.setText("");

        // Resetting person buttons
        personUnchecked(personOneButton);
        personUnchecked(personTwoButton);
        personOneChecked = false;
        personTwoChecked = false;
    }

}