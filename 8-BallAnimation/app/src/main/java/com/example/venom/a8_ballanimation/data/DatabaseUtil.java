package com.example.venom.a8_ballanimation.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import com.example.venom.a8_ballanimation.data.EightballContract.EightballEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Venom on 4/14/2017.
 * This class is for populating the empty database.
 */

public class DatabaseUtil {

    public static void populateDatabase(SQLiteDatabase db){
        if(db == null){
            return;
        }

        //TODO (18) Automate this process by accessing a content provider instead of using hard coded values;

        List<ContentValues> list = new ArrayList<ContentValues>();
        //String key = EightballEntry.COLUMN_ANSWER;
        //String value = "Fuck if I know";
        ContentValues cv = new ContentValues();
        cv.put(EightballEntry.COLUMN_ANSWER, "Fuck if I know");
        cv.put(EightballEntry.COLUMN_CATEGORY, "Default");
        list.add(cv);

        cv = new ContentValues();
        cv.put(EightballEntry.COLUMN_ANSWER, "Suck it up buttercup");
        cv.put(EightballEntry.COLUMN_CATEGORY, "Default");
        list.add(cv);

        cv = new ContentValues();
        cv.put(EightballEntry.COLUMN_ANSWER, "Shit happens");
        cv.put(EightballEntry.COLUMN_CATEGORY, "Default");
        list.add(cv);

        cv = new ContentValues();
        cv.put(EightballEntry.COLUMN_ANSWER, "For fucks sake, yes!");
        cv.put(EightballEntry.COLUMN_CATEGORY, "Default");
        list.add(cv);

        cv = new ContentValues();
        cv.put(EightballEntry.COLUMN_ANSWER, "That's not gonna fuckin' happen");
        cv.put(EightballEntry.COLUMN_CATEGORY, "Default");
        list.add(cv);

        cv = new ContentValues();
        cv.put(EightballEntry.COLUMN_ANSWER, "Boo!");
        cv.put(EightballEntry.COLUMN_CATEGORY, "Spooky");
        list.add(cv);

        cv = new ContentValues();
        cv.put(EightballEntry.COLUMN_ANSWER, "Egad!");
        cv.put(EightballEntry.COLUMN_CATEGORY, "Spooky");
        list.add(cv);

        try{
            db.beginTransaction();
            db.delete(EightballEntry.TABLE_NAME, null, null);
            for(ContentValues c : list){
                db.insert(EightballEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
    }


}
