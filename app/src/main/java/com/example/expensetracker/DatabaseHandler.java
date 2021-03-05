package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "expenseManager";

    // Table name
    private static final String TABLE_EXPENSE = "expense";

    // Table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_TYPE = "type";
    private static final String KEY_MONTANT = "montant";
    private static final String KEY_ADDRESS = "address";
    private static final String CREATE_EXPENSES_TABLE = "";

    public static SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getReadableDatabase();
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_TYPE + " TEXT," + KEY_MONTANT + " TEXT," + KEY_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }


    // Upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);

        // Create tables again
        onCreate(db);
    }

    // Add a new row
    public void addRow(Expense e) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, e.getDate());
        values.put(KEY_TIME, e.getTime());
        values.put(KEY_TYPE, e.getType());
        values.put(KEY_MONTANT, Double.toString(e.getMontant()));
        values.put(KEY_ADDRESS, e.getAddress());


        // Insert row
        db.insert(TABLE_EXPENSE, null, values);
        db.close();

    }

    // Get all rows
    public ArrayList<Expense> getAllRows() {
        ArrayList<Expense> l = new ArrayList<>();

        // Select all query
        String selectQuery = "SELECT * FROM " + TABLE_EXPENSE+ " ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                Expense e = new Expense();
                e.setId(Integer.parseInt(cursor.getString(0)));
                e.setDate(cursor.getString(1));
                e.setTime(cursor.getString(2));
                e.setType(cursor.getString(3));
                e.setMontant(cursor.getDouble(4));
                e.setAddress(cursor.getString(5));

                // Add row to list
                l.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return the list
        return l;
    }


    public int getCurrentId() {
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "SELECT MAX(" + KEY_ID + ") FROM " + TABLE_EXPENSE;
        Cursor c = db.rawQuery(q, null);

        if (c.moveToFirst()) {
            int id = c.getInt(0);
            c.close();
            db.close();
            return id;
        } else {
            return 0;
        }
    }

    public double getTotal() {
        SQLiteDatabase db = this.getWritableDatabase();

        String q = "SELECT SUM(" + KEY_MONTANT + ") FROM " + TABLE_EXPENSE;
        Cursor c = db.rawQuery(q, null);

        if (c.moveToFirst()) {
            double id = c.getInt(0);
            c.close();
            db.close();
            return id;
        } else {
            return 0;
        }
    }

    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSE);
        db.close();
    }

    public void deleteItem(String getID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from  expense WHERE id= '" +getID+ "'");
    }

    //Select total Expense group by date for BarChar expence (queryXData(),queryYData)
    public ArrayList<String> queryXData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> xData = new ArrayList<String>();
        String query="SELECT "+KEY_DATE+ " FROM " + TABLE_EXPENSE+" WHERE "+KEY_MONTANT+">0 GROUP BY "+KEY_DATE;
        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            xData.add(cursor.getString(0));
        }
        cursor.close();
        return xData;
    }
    public ArrayList<String> queryYData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> yData = new ArrayList<String>();
        String query="SELECT SUM(" + KEY_MONTANT + ") FROM " + TABLE_EXPENSE+" WHERE "+KEY_MONTANT+" IS NOT NULL AND "+KEY_MONTANT+"<0 GROUP BY "+KEY_DATE;

        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            yData.add(cursor.getString(0));
        }
        cursor.close();
        return yData;
    }
    //Select total Expense group by date for BarChar expence (queryXData1(),queryYData1)
    public ArrayList<String> queryXData1(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> xData = new ArrayList<String>();
        String query="SELECT "+KEY_DATE+ " FROM " + TABLE_EXPENSE+" WHERE "+KEY_MONTANT+">0 GROUP BY "+KEY_DATE;
        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            xData.add(cursor.getString(0));
        }
        cursor.close();
        return xData;
    }
    public ArrayList<String> queryYData1(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> yData = new ArrayList<String>();
        String query="SELECT SUM(" + KEY_MONTANT + ") FROM " + TABLE_EXPENSE+" WHERE "+KEY_MONTANT+" IS NOT NULL AND "+KEY_MONTANT+">0 GROUP BY "+KEY_DATE;

        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            yData.add(cursor.getString(0));
        }
        cursor.close();
        return yData;
    }

    // PieData Query
    public ArrayList<Expense> pieDataIncome(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Expense> pieData = new ArrayList<>();
        String query="SELECT SUM(" + KEY_MONTANT + ") AS montant,"+KEY_TYPE+" FROM " + TABLE_EXPENSE+" WHERE "+KEY_MONTANT+" IS NOT NULL AND "+KEY_MONTANT+">0 GROUP BY "+KEY_TYPE;

        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Expense e = new Expense();
            e.setMontant(cursor.getDouble(0));
            e.setType(cursor.getString(1));


            pieData.add(e);
        }
        cursor.close();
        return pieData;
    }
    public ArrayList<Expense> pieDataExpense(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Expense> pieData = new ArrayList<>();
        String query="SELECT SUM(" + KEY_MONTANT + ") AS montant,"+KEY_TYPE+" FROM " + TABLE_EXPENSE+" WHERE "+KEY_MONTANT+" IS NOT NULL AND "+KEY_MONTANT+"<0 GROUP BY "+KEY_TYPE;

        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Expense e = new Expense();
            e.setMontant(cursor.getDouble(0));
            e.setType(cursor.getString(1));


            pieData.add(e);
        }
        cursor.close();
        return pieData;
    }

}
