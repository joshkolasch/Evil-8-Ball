package com.example.venom.a8_ballanimation.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Venom on 4/15/2017.
 * This will provide the content for the sql database which stores all of the responses/answers
 */

//TODO (20) Create a unit test for this class. Example on Udacity Lesson 9-15
public class EightballContentProvider extends ContentProvider {

    private EightballDbHelper mEightballDbHelper;

    //variables for UriMatching. This will determine what type of query the app is requesting
    public static final int RESPONSES = 100;
    public static final int RESPONSES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //this will get all items in the responses database
        uriMatcher.addURI(EightballContract.AUTHORITY, EightballContract.PATH_RESPONSES, RESPONSES);
        //this will get a single row in the responses database
        uriMatcher.addURI(EightballContract.AUTHORITY, EightballContract.PATH_RESPONSES + "/#", RESPONSES_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mEightballDbHelper = new EightballDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mEightballDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor returnCursor;

        switch (match){
            case RESPONSES:
                returnCursor = db.query(
                        EightballContract.EightballEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RESPONSES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id= ";
                String[] mSelectionArgs = new String[]{id};

                returnCursor = db.query(
                        EightballContract.EightballEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);

        switch(match){
            case RESPONSES:
                return "vnd.android.cursor.dir" + "/" + EightballContract.AUTHORITY + "/" + EightballContract.PATH_RESPONSES;
            case RESPONSES_WITH_ID:
                return "vnd.android.cursor.item" + "/" + EightballContract.AUTHORITY + "/" + EightballContract.PATH_RESPONSES;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mEightballDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        //insert returns a long value > 0 if successful.
        switch (match){
            case RESPONSES:
                long id = db.insert(EightballContract.EightballEntry.TABLE_NAME, null, values);
                if(id > 0){
                    //success
                    returnUri = ContentUris.withAppendedId(EightballContract.EightballEntry.CONTENT_URI, id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //notify the resolver if the uri has been changed
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mEightballDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case RESPONSES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id= ?";

                rowsDeleted = db.delete(
                        EightballContract.EightballEntry.TABLE_NAME,
                        mSelection,
                        new String[]{id}
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsDeleted > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated;
        int match = sUriMatcher.match(uri);

        switch(match){
            case RESPONSES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                rowsUpdated = mEightballDbHelper.getWritableDatabase().update(
                        EightballContract.EightballEntry.TABLE_NAME,
                        values,
                        "_id= ?",
                        new String[]{id}
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


}
