package com.example.tripexpense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTrip;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    TripDatabaseHelper tripDB;
    ArrayList<String> trip_id, trip_title, trip_destination, trip_date, trip_risk, trip_description;
    //Create CustomAdapter
    private TripAdapter tripAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNav.setNavigateMenuId(this, R.id.mainActivity);

        recyclerViewTrip = findViewById(R.id.recyclerViewTrip);
        empty_imageview = findViewById(R.id.empty_data);
        no_data = findViewById(R.id.no_data);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                intent.putExtra("AddTrip", "AddForm" );
                startActivity(intent);
            }
        });
        //pass data from mySBHelper to MainActivity...
        tripDB = new TripDatabaseHelper(MainActivity.this);
        //initialize new arraylist
        trip_id = new ArrayList<>();
        trip_title = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_risk = new ArrayList<>();
        trip_description = new ArrayList<>();
        storeDisPlayDataInArrays();

        //use customAdapter to find different parameters
        tripAdater = new TripAdapter(MainActivity.this,this , trip_id, trip_title, trip_destination, trip_date, trip_risk,trip_description );
        //after initialize adapter then move to recycleView
        recyclerViewTrip.setAdapter(tripAdater);
        //last show in recycleView
        recyclerViewTrip.setLayoutManager(new LinearLayoutManager((MainActivity.this)));

    }
    //Refresh Data when new update
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDisPlayDataInArrays() {
        //new method in Main...
        Cursor cursor = tripDB.readAllData();
        //Check empty data
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else {
            //Map or read out all data from cursor to MainActivity
            while (cursor.moveToNext()){
                //add each in array
                trip_id.add(cursor.getString(0));
                trip_title.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_risk.add(cursor.getString(4));
                trip_description.add(cursor.getString(5));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
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
            confirmDialogDeleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialogDeleteAll(){
        //Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Big Spender, Delete Expense ?");
        builder.setMessage("Sure!! to Delete All Expense");
        //set Action to confirm performing
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //then Message delete done
                Toast.makeText(MainActivity.this, "Delete All", Toast.LENGTH_SHORT).show();
                //if Yes then use action from DBHelper
                TripDatabaseHelper tDB = new TripDatabaseHelper(MainActivity.this);
                tDB.deleteAllTrip();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
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
