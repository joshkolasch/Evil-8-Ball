package com.example.venom.addphrase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mPhraseET;
    private EditText mCategoryET;
    private EditText mIdET;
    private Button mAddButton;
    private Button mDeleteButton;
    private ScrollView mScrollView;
    private LinearLayout mLinearLayout;

    private Cursor mCursor;

    private final String AUTHORITY = "com.example.venom.a8_ballanimation";
    private final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private final String PATH_RESPONSES = "responses";
    private final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESPONSES).build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhraseET = (EditText) findViewById(R.id.addPhraseEditText);
        mCategoryET = (EditText) findViewById(R.id.addCategoryEditText);
        mIdET = (EditText) findViewById(R.id.idDeleteEditText);
        mAddButton = (Button) findViewById(R.id.addButton);
        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mLinearLayout = (LinearLayout) findViewById(R.id.linLayout);
        //Log.d("MainActivity.java", CONTENT_URI.toString());
        mAddButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);

        mCursor = queryContentProvider();
        populateLayout(mCursor);
    }

    //TODO (1) add a query function. Call this onCreate
    private Cursor queryContentProvider(){
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(CONTENT_URI, null, null, null, null);

        return cursor;
    }

    //TODO (2) add an insert function. Call this one from addButton's onClickListener
    private Uri insertPhrase(ContentValues values){
        Uri uri = getContentResolver().insert(CONTENT_URI, values);
        return uri;
    }

    private void onClickAddButton(){
        String phrase = mPhraseET.getText().toString();
        String category = mCategoryET.getText().toString();

        /*if(phrase.contentEquals("") || phrase.contentEquals(null) || category.contentEquals("") || category.contentEquals(null)){
            Toast.makeText(this, "Must provide a phrase and a category", Toast.LENGTH_LONG);
        }*/

        ContentValues cv = new ContentValues();
        cv.put("answer", phrase);
        cv.put("category", category);
        insertPhrase(cv);
        populateLayout(queryContentProvider());
    }

    private void populateLayout(Cursor cursor){
        mLinearLayout.removeAllViews();
        cursor.moveToFirst();

        int i = 0;
        int total = cursor.getCount();

        for (i =0; i < cursor.getCount(); i++){
            int answerIndex = cursor.getColumnIndex("answer");
            int categoryIndex = cursor.getColumnIndex("category");
            int idIndex = cursor.getColumnIndex("_id");
            String answer = cursor.getString(answerIndex);
            String category = cursor.getString(categoryIndex);
            String id = cursor.getString(idIndex);
            TextView tv = new TextView(this);
            tv.setText(id + ":    " +answer + ", " + category);
            //tv.setText(answer + ", " + category);
            //tv.setText(answer);
            mLinearLayout.addView(tv);
            //i++;
            cursor.moveToNext();
        }

        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addButton:
                onClickAddButton();
                break;
            case R.id.deleteButton:
                onClickDeleteButton();
                break;
        }
    }
    //TODO (3) add a delete function. Call this one from deleteButton's onClickListener
    private void onClickDeleteButton(){
        String id = mIdET.getText().toString();

        /*if(id.contentEquals("") || id.contentEquals(null)){
            Toast.makeText(this, "Please enter valid id of phrase you want to delete", Toast.LENGTH_LONG);
            return;
        }*/
        deletePhrase(id);
    }

    private int deletePhrase(String id){
        Uri uri = CONTENT_URI.buildUpon().appendPath(id).build();
        int response = getContentResolver().delete(uri, null, null);
        if(response > 0){
            populateLayout(queryContentProvider());
        }
        return response;
    }


}
