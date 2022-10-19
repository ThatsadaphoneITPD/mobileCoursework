package com.example.tripexpense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class EditTripActivity extends AppCompatActivity {

    RadioButton radioButton;
    RadioGroup editRadio_Risk;
    EditText et_titleInput, et_destinationInput, et_dateInput, et_descriptionInput;
    Button datePicker, editTrip_button, seeExpense_button;
    DatePickerDialog.OnDateSetListener setListener;


    //Store extra data in string from mainActivity
    String id, title, destination, date, risk, description;
    String trip_id, trip_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        editRadio_Risk = findViewById(R.id.editRadio_Risk);
        et_titleInput= findViewById(R.id.et_titleInput);
        et_destinationInput = findViewById(R.id.et_DestinationInput);
        et_dateInput = findViewById(R.id.et_DateInput);
        et_descriptionInput = findViewById(R.id.et_DescriptionInput);
        datePicker= findViewById(R.id.datePicker);
        editTrip_button = findViewById(R.id.editTrip_button);
        seeExpense_button = findViewById(R.id.seeExpense_button);
        seeExpense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTripActivity.this, ExpenseMainActivity.class);
                trip_id = getIntent().getStringExtra("id");
                trip_title = getIntent().getStringExtra("title");
                intent.putExtra("trip_id", trip_id);
                intent.putExtra("trip_name", trip_title);
                startActivity(intent);
            }
        });

        //First call this to read Old Trip to edit
        getAndSetIntentData();

        //DatePicker
        Calendar calendarTrip = Calendar.getInstance();
        final int year = calendarTrip.get(Calendar.YEAR);
        final int month = calendarTrip.get(Calendar.MONTH);
        final int day = calendarTrip.get(Calendar.DAY_OF_MONTH);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1 ;
                        String date = day + "/" + month + "/" + year;
                        et_dateInput.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        editTrip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int riskId = editRadio_Risk.getCheckedRadioButtonId();
                radioButton =findViewById(riskId);
                if (riskId == -1){
                    Toast.makeText(EditTripActivity.this, "No Select Risk", Toast.LENGTH_SHORT).show();
                }
                else {
                    TripDatabaseHelper myDB = new TripDatabaseHelper(EditTripActivity.this);
                    //use ToString Trim for add new Changed data
                    title = et_titleInput.getText().toString().trim();
                    destination = et_destinationInput.getText().toString().trim();
                    date = et_dateInput.getText().toString().trim();
                    risk = radioButton.getText().toString().trim();
                    description = et_descriptionInput.getText().toString().trim();
                    myDB.updateTrip(id, title, destination, date, risk, description);
                    //Close current activity then back to MainActivity
                    finish();
                }
            }
        });
    }
    void getAndSetIntentData(){
        if(
            //First of all by Directly Pass ExtraOf MainACT to UpdateACT her
                getIntent().hasExtra("id")&&getIntent().hasExtra("title")&& getIntent().hasExtra("destination")&& getIntent().hasExtra("date")&& getIntent().hasExtra("risk")&& getIntent().hasExtra("description")
        ){
            //get Data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            risk = getIntent().getStringExtra("risk");
            description = getIntent().getStringExtra("description");
            Toast.makeText(this, "Get Extra data", Toast.LENGTH_SHORT).show();
//            setting intent data
            et_titleInput.setText(title);
            et_destinationInput.setText(destination);
            et_dateInput.setText(date);
            et_descriptionInput.setText(description);
            //setting extra Risk to radio button
            String yes = "YES";
            String no = "NO";
            if(risk.equals(yes)) {
                editRadio_Risk.check(R.id.YES);
            } else if (risk.equals(no)){
                editRadio_Risk.check(R.id.NO);
            }
        }else{
            Toast.makeText(this, "No data from mainLayout of Adapter", Toast.LENGTH_SHORT).show();
        }
    }

    //Create each option menu
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_bartop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //this method will select to trigger each menu action in List menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //select case in menu
        if(item.getItemId() == R.id.delete_all){
            confirmDialogDelete();
        }
        return super.onOptionsItemSelected(item);
    }


    void confirmDialogDelete(){
        //Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title +" Trip ?");
        builder.setMessage("Do you really want to Delete" + title + " ?");
        //set Action to confirm performing
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //if Yes then use action from DBHelper
                TripDatabaseHelper myDB = new TripDatabaseHelper(EditTripActivity.this);
                myDB.deleteOnRowTrip(id);
                finish();
            }
        });
        //Set Cancel action
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //this will not execute any action
            }
        });
        //this will create Modal pop-up
        builder.create().show();
    }
}