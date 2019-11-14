package com.example.tree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";
    //Define facts about the database to set it up including its name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores.db";

    //Define tables in the database
    public static final String TABLE_SCORES = "scores";             // table name
    public static final String COLUMN_ID = "_id";                   // unique id for the entry
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SCORE = "score";

    //constructor
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    /*
    Here we create the specific query needed to create the table.  The syntax is EXTREMELY important.
    The execSQL method is called to execute this request to create the database as defined by the query.
     */

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SCORES + "(_id INTEGER, date INTEGER, score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_SCORES;
        db.execSQL(query);
        onCreate(db);
    }


    public void addScore(Scores score) {
        // ContentValues is like a datastructure that allows you to attach values
        // it is similar to how we would put items into an intent

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, score.get_id());
        values.put(COLUMN_DATE, score.get_date());
        values.put(COLUMN_SCORE, score.get_score());
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_SCORES, null, values);  // inserts these values into this table

        Log.d(TAG, "Tried to insert score, result was " + result);

        db.close();             // need to close the database when we are done modifying it.
    }





    public int getDateForGivenID(int id){

        int date = -1;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor entry = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SCORES + " WHERE _id = " +  id , null);


        if (entry.moveToFirst()) {
            date = entry.getInt(entry.getColumnIndex(COLUMN_DATE));
        }

        return date;
    }


    public int getScoreForGivenDate(int date){
        int score = -1;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor entry = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SCORES + " WHERE " +COLUMN_DATE + "=" +  date, null);

        if (entry.moveToFirst()) {
            score = entry.getInt(entry.getColumnIndex(COLUMN_SCORE));
        }

        entry.close();

        return score;

    }

    public int getScoreForGivenID(int id){
        int score = -1;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor entry = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SCORES + " WHERE " +COLUMN_ID + "=" +  id, null);

        if (entry.moveToFirst()) {
            score = entry.getInt(entry.getColumnIndex(COLUMN_SCORE));
        }

        entry.close();

        return score;

    }

    public int getIDForGivenDate(int date){
        int id = -1;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor entry = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SCORES + " WHERE " +COLUMN_DATE + "=" +  date, null);

        if (entry.moveToFirst()) {
            id = entry.getInt(entry.getColumnIndex(COLUMN_ID));
        }

        entry.close();

        return id;

    }









    public boolean hasData(){
        boolean hasData;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCORES + " WHERE 1";
        // This means to select all from the database

        // The cursor will extract the entries from the database
        Cursor c = db.rawQuery(query, null);

        if(c.getCount()==0){
            hasData = false;
        }

        else{
            hasData = true;
        }

        return hasData;
    }

    public void clearDatabase(String TABLE_NAME) {
        SQLiteDatabase db = getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    public int getProfilesCount(String TABLE_NAME) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


}