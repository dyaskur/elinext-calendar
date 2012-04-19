package com.rememberme.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DaySQLiteOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_DAY_NOTES = "day_notes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SYMPTOMS = "symptoms";
	public static final String COLUMN_MENSTRUATION = "menstruation";
	public static final String COLUMN_STIMMUNGS = "stimmungs";
	public static final String COLUMN_DAY_NOTE = "day_note";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_BEGIN_OR_END_PILLE_DATE = "pille_date";
	public static final String COLUMN_ARZTTERMIN = "arzttermin";
	public static final String COLUMN_MONTH = "month";
	public static final String COLUMN_INTIM = "intim";

	private static final String DATABASE_NAME = "day_notes.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_DAY_NOTES + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_MENSTRUATION
			+ " text," + COLUMN_SYMPTOMS + " text," + COLUMN_STIMMUNGS
			+ " text," + COLUMN_DAY_NOTE + " text," + COLUMN_DATE + " text,"
			+ COLUMN_BEGIN_OR_END_PILLE_DATE + " text," + COLUMN_ARZTTERMIN
			+ " text," + COLUMN_MONTH + " integer," + COLUMN_INTIM + " text" + ");";

	public DaySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DaySQLiteOpenHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_NOTES);
		onCreate(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);

	}

}
