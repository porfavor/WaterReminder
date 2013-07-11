package com.coffeebean.waterreminder.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CustomDbManager {
	private Context mContext;
	private CustomDbHelper mDBHelper;
	private SQLiteDatabase mSqliteDatabase;

	public CustomDbManager(Context context) {
		mContext = context;
	}

	// ********** 数据库操作 **********
	public void open() {
		mDBHelper = new CustomDbHelper(mContext);
		mSqliteDatabase = mDBHelper.getWritableDatabase();
	}

	public void close() {
		if (mSqliteDatabase != null) {
			mSqliteDatabase.close();
		}
		if (mDBHelper != null) {
			mDBHelper.close();
		}
	}

	// ********** 数据操作 **********
	/**
	 * 向指定的数据库表中插入一条记录
	 * 
	 * @param tableName
	 *            String 表名
	 * @param values
	 *            ContentValues 字段名-值对
	 */
	public long insertData(String tableName, ContentValues values) {
		return mSqliteDatabase.insert(tableName, null, values);
	}

	/**
	 * 根据查询条件获取数据库表中的数据
	 * 
	 * @param tableName
	 *            String 表名
	 * @param columns
	 *            String[] 要查询的列名
	 * @param selection
	 *            过滤条件(不包含 WHERE). 为null将会返回所有数据。
	 * @return Cursor 查询数据集
	 */
	public Cursor queryData(String tableName, String[] columns, String selection) {
		return mSqliteDatabase.query(tableName, columns, selection, null, null,
				null, null);
	}

	/**
	 * 根据查询条件获取数据库表中的数据
	 * 
	 * @param tableName
	 *            String 表名
	 * @param columns
	 *            String[] 要查询的列名
	 * @param selection
	 *            过滤条件(不包含 WHERE). 为null将会返回所有数据。
	 * @return Cursor 查询数据集
	 */
	public Cursor queryData(String tableName, String[] columns,
			String selection, String[] selectionArgs) {
		return mSqliteDatabase.query(tableName, columns, selection,
				selectionArgs, null, null, null);
	}

	/**
	 * 更新数据
	 * 
	 * @param tableName
	 *            表名
	 * @param values
	 *            更新字段名-值对。 null is a valid value that will be translated to
	 *            NULL.
	 * @param whereClause
	 *            过滤条件（可选）。为null将会更新所有数据
	 * @param whereArgs
	 * @return int 影响记录的条数
	 */
	public int updateData(String tableName, ContentValues values,
			String whereClause, String[] whereArgs) {
		return mSqliteDatabase
				.update(tableName, values, whereClause, whereArgs);
	}

	/**
	 * 从指定的表中根据条件删除数据
	 * 
	 * @param tableName
	 *            表名
	 * @param whereClause
	 *            过滤条件（可选）。为null将会删除所有数据
	 * @param whereArgs
	 * @return int 影响记录的条数
	 */
	public int deleteData(String tableName, String whereClause,
			String[] whereArgs) {
		return mSqliteDatabase.delete(tableName, whereClause, whereArgs);
	}

	/**
	 * 删除数据库表
	 * 
	 * @param tableName
	 *            String 表名
	 * @return boolean 操作结果
	 */
	protected boolean dropTable(String tableName) {
		boolean result = false;

		try {
			mSqliteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
