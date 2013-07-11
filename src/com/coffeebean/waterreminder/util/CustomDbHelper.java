package com.coffeebean.waterreminder.util;

import com.coffeebean.waterreminder.common.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomDbHelper extends SQLiteOpenHelper {
	private static final String mDbName = Constants.DATABASE_NAME;
	private static final int mDbVersion = Constants.DATABASE_VERSION;

	public CustomDbHelper(Context context) {
		super(context, mDbName, null, mDbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ Constants.DATA_TABLE
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, idx INTEGER, time TIME )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
		//
	}
}
