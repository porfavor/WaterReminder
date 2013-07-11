package com.coffeebean.waterreminder.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomDbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "water_reminder.db";  
    private static final int DATABASE_VERSION = 1;  
      
    public CustomDbHelper(Context context) {  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
  
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        db.execSQL("CREATE TABLE IF NOT EXISTS remind_time" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, idx INTEGER, age TIME )");  
    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        //db.execSQL("ALTER TABLE person ADD COLUMN other STRING"); 
    	//
    }  
}
