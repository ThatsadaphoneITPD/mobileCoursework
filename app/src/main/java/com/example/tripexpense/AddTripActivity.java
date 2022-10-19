package com.example.tripexpense;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity {
    RadioButton radioButton;
    RadioGroup radioGroupRisk;
    EditText trip_titleInput, trip_destinationInput, trip_dateInput, trip_descriptionInput;
    TextView AddOrEdit;
    Button datePicker, addTrip_button;
    DatePickerDialog.OnDateSetListener setListener;
    String addTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        radioGroupRisk = findViewById(R.id.radio_Risk);
        trip_titleInput= findViewById(R.id.trip_titleInput);
        trip_destinationInput = findViewById(R.id.trip_DestinationInput);
        trip_dateInput = findViewById(R.id.trip_DateInput);
        trip_descriptionInput = findViewById(R.id.trip_descriptionInput);
        datePicker= findViewById(R.id.datePicker);
        addTrip_button = findViewById(R.id.addTrip_button);
        AddOrEdit = findViewById(R.id.addOrEdit);

        Calendar calendarTrip = Calendar.getInstance();
        final int year = calendarTrip.get(Calendar.YEAR);
        final int month = calendarTrip.get(Calendar.MONTH);
        final int day = calendarTrip.get(Calendar.DAY_OF_MONTH);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1 ;
                        String date = day + "/" + month + "/" + year;
                        trip_dateInput.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //Check Form
        addTrip = getIntent().getStringExtra("AddTrip");
        //Toast.makeText(this, "StatusAdd: " + addTrip + " Edit: "+ editTrip, Toast.LENGTH_SHORT).show();
        if (getIntent().hasExtra("AddTrip")){
            AddOrEdit.setText(addTrip);
        }


        addTrip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int riskId = radioGroupRisk.getCheckedRadioButtonId();
                radioButton =findViewById(riskId);
                if (riskId == -1){
                    Toast.makeText(AddTripActivity.this, "No Select Risk", Toast.LENGTH_SHORT).show();
                }
                else if (trip_titleInput.length() == 0 || trip_dateInput.length() == 0 || trip_destinationInput.length() == 0 || trip_descriptionInput.length() == 0){
                    ValidationInput("Input Empty, Pls Fulfill","Trip Data miss:"
                            + "\n\n"+ "Title"+":\t"+ trip_titleInput.getText()
                            + "\n\n"+ "Destination"+":\t"+ trip_destinationInput.getText()
                            + "\n\n"+ "Date"+":\t"+ trip_dateInput.getText()
                            + "\n\n"+ "Risk"+":\t"+ radioButton.getText()
                            + "\n\n"+ "Description"+":\t"+ trip_descriptionInput.getText(), true, false);
                }
                else {
                    //all Fulfil then Add dat to SQL DB white check Form
                    if (getIntent().hasExtra("AddTrip")){
                        ValidationInput("Confirm to Create New aTrip","Trip Data:"
                                + "\n\n"+ "Title"+":\t"+ trip_titleInput.getText()
                                + "\n\n"+ "Destination"+":\t"+ trip_destinationInput.getText()
                                + "\n\n"+ "Date"+":\t"+ trip_dateInput.getText()
                                + "\n\n"+ "Risk"+":\t"+ radioButton.getText()
                                + "\n\n"+ "Description"+":\t"+ trip_descriptionInput.getText(), true, true);
                    }
                }
            }
        });


    }
    void ValidationInput(String title, String message, boolean cancel, boolean addAction){
        //Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        //Set Cancel action
        if (cancel == true){
            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //this will not execute any action
                }
            });
        }
        if (addAction == true){
            builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //if Yes then use action from DBHelper
                    TripDatabaseHelper myDB = new TripDatabaseHelper(AddTripActivity.this);
                    myDB.addTrip(
                            trip_titleInput.getText().toString().trim(),
                            trip_destinationInput.getText().toString().trim(),
                            trip_dateInput.getText().toString().trim(),
                            radioButton.getText().toString().trim(),
                            trip_descriptionInput.getText().toString().trim()
                    );
                    //Refresh Activity
                    Intent intent = new Intent(AddTripActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        //this will create Modal pop-up
        builder.create().show();
    }
}