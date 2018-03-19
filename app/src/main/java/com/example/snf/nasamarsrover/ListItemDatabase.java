package com.example.snf.nasamarsrover;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by SNF on 14-3-2018.
 */

public class ListItemDatabase extends SQLiteOpenHelper {
    private static final String TAG = "ListItemDatabase";

    private static final String DATABASE_NAME = "ItemList.db";
    private static final String TABLE_NAME = "ItemList_table";
    private static final String COL_1 = "KEY";
    private static final String COL_2 = "ID";
    private static final String COL_3 = "IMAGE_URL";
    private static final String COL_4 = "CAMERA_NAME";




    public ListItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Creates a database calls Oncreate");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                COL_2 + " INTEGER PRIMARY KEY, " +
                COL_3 + " TEXT NOT NULL, " +
                COL_4 + " TEXT NOT NULL "+ ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "upgrade database calls onUpgrade");


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addListItem(ListItem listItem){
        Log.d(TAG, "calls addlistItem... Adds: " + listItem.getCameraName() + "Name: " + listItem.getImageURL());

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, listItem.getId());
        contentValues.put(COL_3, listItem.getImageURL());
        contentValues.put(COL_4, listItem.getCameraName());

        SQLiteDatabase db = this.getWritableDatabase();
        Long id = db.insert(TABLE_NAME,null, contentValues);
        db.close();
    }

    public ArrayList<ListItem> getAllItems(){
        Log.d(TAG, "Calls getAllItems");

        ArrayList<ListItem> listItems = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex(COL_2));
            String imageURL = cursor.getString(cursor.getColumnIndex(COL_3));
            String cameraName = cursor.getString(cursor.getColumnIndex(COL_4));

            ListItem listItem = new ListItem(id, imageURL, cameraName);
            listItems.add(listItem);
            cursor.moveToNext();
        }
        return listItems;
    }




}
