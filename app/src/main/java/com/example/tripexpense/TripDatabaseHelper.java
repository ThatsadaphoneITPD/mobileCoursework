package com.example.tripexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import es.dmoral.toasty.Toasty;


public class TripDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Trip3.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_TRIP = "my_trip";
    public static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "trip_title";
    private static final String COLUMN_DESTINATION = "trip_destination";
    private static final String COLUMN_DATE = "trip_date";
    private static final String COLUMN_RISK = "trip_risk";
    private static final String COLUMN_DESCRIPTION = "trip_description";
    //Expense
    public static final String TABLE_EXPENSE = "my_expense";
    private static final String COLUMN_EX_ID = "_id";
    private static final String COLUMN_EX_TYPE = "expense_type";
    private static final String COLUMN_EX_AMOUNT = "expense_amount";
    private static final String COLUMN_EX_DATE = "expense_date";
    public static final String COLUMN_TRIP_ID = "trip_id";

    public TripDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_TRIP +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESTINATION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_RISK + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT) ;";
        String exQuery = " CREATE TABLE " + TABLE_EXPENSE +
                " (" + COLUMN_EX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EX_TYPE + " TEXT, " +
                COLUMN_EX_AMOUNT + " INTEGER, " +
                COLUMN_EX_DATE + " TEXT, " +
                COLUMN_TRIP_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ID + ") REFERENCES "
                + TABLE_TRIP + "("
                + COLUMN_ID + ") ON DELETE CASCADE"
                +")";
        db.execSQL(query);
        db.execSQL(exQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }

    //Create new trip
    void addTrip(String title, String destination, String date, String risk, String description) {
        //new method
        //Get write can write data in out table
        SQLiteDatabase db = this.getWritableDatabase();
        // create new object data
        ContentValues cvTrip = new ContentValues();
        //pass to DB table
        cvTrip.put(COLUMN_TITLE, title);
        cvTrip.put(COLUMN_DESTINATION, destination);
        cvTrip.put(COLUMN_DATE, date);
        cvTrip.put(COLUMN_RISK, risk);
        cvTrip.put(COLUMN_DESCRIPTION, description);
        //sort new insertDB as Result
        long result = db.insert(TABLE_TRIP, null, cvTrip);
        // if fail in insert data to table will message user
        if(result == -1){
            Toast.makeText(context, "Failed Insert Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully add! new Trip", Toast.LENGTH_SHORT).show();
        }
    }
    //Create new  Expense
    void addExpense(String type, int amount, String date, int trip_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // create new object data
        ContentValues dbExpense = new ContentValues();
        //pass to DB table
        dbExpense.put(COLUMN_EX_TYPE, type);
        dbExpense.put(COLUMN_EX_AMOUNT, amount);
        dbExpense.put(COLUMN_EX_DATE, date);
        dbExpense.put(COLUMN_TRIP_ID, trip_id);

        long result = db.insert(TABLE_EXPENSE, null, dbExpense);
        // if fail in insert data to table will message user
        if(result == -1){
            Toasty.error(context, "Failed Insert Data", Toast.LENGTH_SHORT).show();
        } else {
            Toasty.success(context, "Successfully add! new Expense" + COLUMN_EX_TYPE, Toast.LENGTH_SHORT,true).show();
        }
    }

    //Display all Data from SQLife table to MainActivity
    Cursor readAllData(){
        //select my_trip table to return new Cursor Object to Query
        String query = "SELECT * FROM " + TABLE_TRIP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    };
    //Display Expense
    Cursor readAllEXpense(){
        //select my_trip table to return new Cursor Object to Query
        String query = "SELECT * FROM " + TABLE_EXPENSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    };
    //Display Related
    public Cursor readRelateExpense(String trip_id){
        //select my_trip table to return new Cursor Object to Query
        String query = "SELECT * FROM " + TABLE_EXPENSE + " where " + COLUMN_TRIP_ID + " like '%" + trip_id + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    };

    //Edit trip
    void updateTrip(String id, String title, String destination, String date, String risk, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues editData = new ContentValues();
        editData.put(COLUMN_TITLE, title);
        editData.put(COLUMN_DESTINATION, destination);
        editData.put(COLUMN_DATE, date);
        editData.put(COLUMN_RISK, risk);
        editData.put(COLUMN_DESCRIPTION, description);
        //sort new insertDB as Result
        long result = db.update(TABLE_TRIP, editData, "_id=?", new String[] {id});
        // if fail in insert data to table will message user
        if(result == -1){
            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully update! a Trip", Toast.LENGTH_SHORT).show();
        }
    }
    //Edit Expense
    void updateExpense(String id, String type, String amount, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COLUMN_EX_TYPE, type);
        data.put(COLUMN_EX_AMOUNT, amount);
        data.put(COLUMN_DATE, date);
        //sort new insertDB as Result
        long result = db.update(TABLE_EXPENSE, data, "_id=?", new String[] {id});
        // if fail in insert data to table will message user
        if(result == -1){
            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully update! a Book", Toast.LENGTH_SHORT).show();
        }
    }

    //Delete one Trip
    void deleteOnRowTrip(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        //sort new insertDB as Result
        //Delete Expense First
        db.delete(TABLE_EXPENSE, "trip_id=?", new String[] {id});
        //Delete Row Trip
        long result = db.delete(TABLE_TRIP, "_id=?", new String[] {id});
        // if fail in insert data to table will message user
        if(result == -1){
            Toast.makeText(context, " Failed Delete Trip", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Delete! a Trip", Toast.LENGTH_SHORT).show();
        }

    }
    //Delete All Trip
    void deleteAllTrip(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM"+ " " + TABLE_TRIP);
        db.execSQL("DELETE FROM"+ " " + TABLE_EXPENSE);

    }
    void deleteAllRelatedEX(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        //sort new insertDB as Result
        long result = db.delete(TABLE_EXPENSE, "trip_id=?", new String[] {id});
        if(result == -1){
            Toast.makeText(context, " Failed Delete Related Expense", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Delete! Related Expense", Toast.LENGTH_SHORT).show();
        }
    }
    //Delete All Expense
    void Expense(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM"+ " " + TABLE_EXPENSE);

    }

    //Search Trip
    public Cursor searchTrip(String keyword) {
        String sql = "select * from " + TABLE_TRIP + " where " + COLUMN_TITLE + " like '%" + keyword + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if(keyword != null|| keyword.length()>0){
            cursor = db.rawQuery( sql, null);
        }else{
            cursor = null;
        }
        return cursor;
    }

}
