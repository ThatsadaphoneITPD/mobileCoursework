package com.example.tripexpense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSearch;
    SearchView searchView;
    BottomNavigationView bottomNavigationView;
    TripDatabaseHelper tripDB;

    ArrayList<String> trip_id, trip_title, trip_destination, trip_date, trip_risk, trip_description;
    private TripAdapter tripAdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerViewSearch = findViewById(R.id.recyclerViewSearch);
        searchView = findViewById(R.id.SearchViewBar);
        searchView.clearFocus();

        //Navigation
        BottomNav.setNavigateMenuId(this, R.id.searchActivity);

        //pass data from mySBHelper to MainActivity...
        tripDB = new TripDatabaseHelper(SearchActivity.this);
        //initialize new arraylist
        trip_id = new ArrayList<>();
        trip_title = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_risk = new ArrayList<>();
        trip_description = new ArrayList<>();
        // perform set on query text listener event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //search after input fill
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Clear Old search's result
                clearOldResult();
                //after Clear then get Next New result
                storeDisPlayDataInArrays(query);
                return false;
            }
            //search one by one input while input change
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void clearOldResult() {
        trip_id.clear();
        trip_title.clear();
        trip_destination.clear();
        trip_date.clear();
        trip_risk.clear();;
        trip_description.clear();
    }

    void storeDisPlayDataInArrays(String trip) {
        //new method in Main...
        Cursor cursor = tripDB.searchTrip(trip);
        //Check empty data
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Found", Toast.LENGTH_SHORT).show();

        } else {
            //Map or read out all data from cursor to MainActivity
            while (cursor.moveToNext()) {
                //add each in array
                trip_id.add(cursor.getString(0));
                trip_title.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_risk.add(cursor.getString(4));
                trip_description.add(cursor.getString(5));

            }
            tripAdater = new TripAdapter(SearchActivity.this, this, trip_id, trip_title, trip_destination, trip_date, trip_risk, trip_description);
            recyclerViewSearch.setAdapter(tripAdater);
            recyclerViewSearch.setLayoutManager(new LinearLayoutManager((SearchActivity.this)));
        }
    }

}