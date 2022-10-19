package com.example.tripexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class AddExpenseActivity extends AppCompatActivity {

    String[] items = {"Food", "Design", "SHOP"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    String typeExpense = "";
    EditText amount_input, date_input;
    Button datePicker, addExpense_button;
    String trip_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        amount_input = findViewById(R.id.amountExpense);
        date_input = findViewById(R.id.dateExpense);
        datePicker = findViewById(R.id.date_pickerExpense);
        addExpense_button = findViewById(R.id.addExpense_button);
        //Set custom Toasty Size in this.activity
        Toasty.Config.getInstance().setTextSize(26).apply();
        if (
            //First of all by Directly Pass ExtraOf MainACT to UpdateACT her
                getIntent().hasExtra("trip_id")
        ) {
            //store Data from intent
            trip_id = getIntent().getStringExtra("trip_id");
            Toast.makeText(this, "Trip: " + trip_id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Extra Trip_id", Toast.LENGTH_SHORT).show();
        }

        //Select Dropdown
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdrown_item, items);
        //select DropDrown type
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                typeExpense = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AddExpenseActivity.this, "Items" + typeExpense, Toast.LENGTH_SHORT).show();
            }
        });
        //Date
        Calendar calendarTrip = Calendar.getInstance();
        final int year = calendarTrip.get(Calendar.YEAR);
        final int month = calendarTrip.get(Calendar.MONTH);
        final int day = calendarTrip.get(Calendar.DAY_OF_MONTH);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        date_input.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        addExpense_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (typeExpense == null || typeExpense.trim().isEmpty()) {
                    Toasty.warning(AddExpenseActivity.this, "Type not Fill" + typeExpense, Toast.LENGTH_SHORT).show();
                } else if (amount_input.length() == 0) {
//                    Toasty.warning(AddExpenseActivity.this, "Amount not Fill" + typeExpense, Toast.LENGTH_SHORT).show();
                    Drawable iconWarn = getResources().getDrawable(R.drawable.ic_baseline_warning_24);
                    Toasty.normal(AddExpenseActivity.this, "This is a toast message with icon", iconWarn).show();
                } else if (date_input.length() == 0) {
                    ToastCustom("Date's not picked");
                } else {
                    //fulfil Successful
                    TripDatabaseHelper myDB = new TripDatabaseHelper(AddExpenseActivity.this);
                    myDB.addExpense(
                            typeExpense.trim(),
                            Integer.parseInt(amount_input.getText().toString().trim()),
                            date_input.getText().toString().trim(),
                            Integer.parseInt(trip_id)
                    );
                    //Refresh Activity
                    Intent intent = new Intent(AddExpenseActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @SuppressLint("ShowToast")
    public void ToastCustom(String textMessage) {
        Toast toast = Toast.makeText(this, textMessage, Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(30);
        text.setTextColor(Color.WHITE);
        //toast.setGravity(Gravity.CENTER, 0, 200);
        //toast.setGravity(Gravity.TOP, 0, 200);
        view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        //Then Pop up
        toast.show();
    }
}