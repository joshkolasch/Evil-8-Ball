package com.example.venom.a8_ballanimation.data;

import android.provider.BaseColumns;

/**
 * Created by Venom on 4/14/2017.
 * This will house the structure of the SQLite database.
 * This will contain all of the responses that the user will see when interacting with the app.
 */

//Complete TODO (14) Finish Contract design
//Complete TODO (15) Implement Contract class in MainActivity and replace hardcoded String array
//Complete TODO (16) Create a Unit Test for this class. Example on Udacity Lesson 7-6
public class EightballContract {

    public static final class EightballEntry implements BaseColumns {

        //BaseColumns by default will inherit an ID field, so it is unnecessary to declare it here.
        public static final String TABLE_NAME = "eightball";

        public static final String COLUMN_ANSWER = "answer";

        public static final String COLUMN_CATEGORY = "category";

        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
