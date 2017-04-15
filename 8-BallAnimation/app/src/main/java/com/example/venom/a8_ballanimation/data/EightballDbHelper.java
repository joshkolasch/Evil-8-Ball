package com.example.venom.a8_ballanimation.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.venom.a8_ballanimation.data.EightballContract.EightballEntry;

/**
 * Created by Venom on 4/14/2017.
 * Responsible for creating the database based on the structure defined in the Contract class.
 * Note: This is an empty instance of the database. It must be populated with data from the DatabaseUtil class
 */

//TODO (17) Create a Unit Test for this class. Example on Udacity Lesson 7-9
public class EightballDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eightball.db";

    private static final int DATABASE_VERSION = 1;

    public EightballDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql statement for creating the database
        final String SQL_CREATE_EIGHTBALL_TABLE = "CREATE TABLE " +
                EightballEntry.TABLE_NAME + " (" +
                EightballEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EightballEntry.COLUMN_ANSWER + " TEXT NOT NULL," +
                EightballEntry.COLUMN_CATEGORY + " TEXT NOT NULL," +
                EightballEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

        //executes the sql statement created above and creates the database
        db.execSQL(SQL_CREATE_EIGHTBALL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + EightballEntry.TABLE_NAME);
        onCreate(db);
    }
}
