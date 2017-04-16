package com.example.venom.a8_ballanimation.data;

import android.net.Uri;
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

    public static final String AUTHORITY = "com.example.venom.a8_ballanimation";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RESPONSES = "responses";


    public static final class EightballEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESPONSES).build();

        //BaseColumns by default will inherit an ID field, so it is unnecessary to declare it here.
        public static final String TABLE_NAME = "responses";

        public static final String COLUMN_ANSWER = "answer";

        public static final String COLUMN_CATEGORY = "category";

        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
