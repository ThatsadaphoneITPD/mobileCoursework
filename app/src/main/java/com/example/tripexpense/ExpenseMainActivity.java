package com.example.tripexpense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpenseMainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewExpense;
    FloatingActionButton addExpense_button;
    ImageView empty_imageview;
    TextView no_data, trip_belong;

    TripDatabaseHelper expenseDB;
    ArrayList<String> expense_id, expense_type, expense_amount, expense_date;
    private ExpenseAdapter expenseAdapter;
    BottomNavigationView bottomNavigationView;
    //Data From Trip_id
    String trip_id, trip_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_main);

        recyclerViewExpense = findViewById(R.id.recyclerViewExpense);
        empty_imageview = findViewById(R.id.empty_exData);
        no_data = findViewById(R.id.no_ExData);
        trip_belong = findViewById(R.id.Trip_belong);
        addExpense_button = findViewById(R.id.addExpense_button);

        //Check Trip_id
        if (getIntent().hasExtra("trip_id") && getIntent().hasExtra("trip_name")) {
            //store Data from intent
            trip_id = getIntent().getStringExtra("trip_id");
            trip_name = getIntent().getStringExtra("trip_name");
            trip_belong.setText("Trip: " + trip_name);
        }else {
            trip_belong.setText("Expense List");
            //hide addButton if at Global list
            addExpense_button.setVisibility(View.GONE);
        }

        addExpense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseMainActivity.this, AddExpenseActivity.class);
                intent.putExtra("trip_id", trip_id);
                startActivity(intent);
            }
        });
        BottomNav.setNavigateMenuId(this, R.id.expenseMainActivity2);


        //pass data from mySBHelper to MainActivity...
        expenseDB = new TripDatabaseHelper(ExpenseMainActivity.this);
        //initialize new arraylist
        expense_id = new ArrayList<>();
        expense_type = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_date = new ArrayList<>();
        storeDisPlayDataInArrays();

//        use customAdapter to find different parameters
        expenseAdapter = new ExpenseAdapter(ExpenseMainActivity.this, this, expense_id, expense_type, expense_amount, expense_date);
        recyclerViewExpense.setAdapter(expenseAdapter);
        recyclerViewExpense.setLayoutManager(new LinearLayoutManager((ExpenseMainActivity.this)));

    }

    //Refresh Data when new update

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        if (getIntent().hasExtra("trip_id")) {
            MenuInflater inflater =getMenuInflater();
            inflater.inflate(R.menu.menu_bartop, menu);
        }else {
            //No Menu

        }
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //select case in menu
        if(item.getItemId() == R.id.delete_all){
            TripDatabaseHelper expenseDB = new  TripDatabaseHelper(ExpenseMainActivity.this);
            expenseDB.deleteAllRelatedEX(trip_id);
            Intent intent = new Intent(ExpenseMainActivity.this,ExpenseMainActivity.class);
            trip_id = getIntent().getStringExtra("trip_id");
            intent.putExtra("trip_id", trip_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    void storeDisPlayDataInArrays() {
        //new method in Main...
        Cursor cursor;
        //check if this is Trip's expense or All Expense then change menthod
        if (getIntent().hasExtra("trip_id")) {

            cursor = expenseDB.readRelateExpense(trip_id);
        }else {
            cursor = expenseDB.readAllEXpense();
        }

        //Check empty data
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            //Map or read out all data from cursor to MainActivity
            while (cursor.moveToNext()) {
                //add each in array
                expense_id.add(cursor.getString(0));
                expense_type.add(cursor.getString(1));
                expense_amount.add(cursor.getString(2));
                expense_date.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

}