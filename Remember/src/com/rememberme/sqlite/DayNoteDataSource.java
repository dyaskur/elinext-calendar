package com.rememberme.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rememberme.entity.DayNote;

public class DayNoteDataSource {
	private static final String TAG = "DayNoteDataSource";

	private SQLiteDatabase database;
	private DaySQLiteOpenHelper dbHelper;
	private String[] allColumns = { DaySQLiteOpenHelper.COLUMN_ID,
			DaySQLiteOpenHelper.COLUMN_DAY_NOTE,
			DaySQLiteOpenHelper.COLUMN_MENSTRUATION,
			DaySQLiteOpenHelper.COLUMN_SYMPTOMS,
			DaySQLiteOpenHelper.COLUMN_STIMMUNGS,
			DaySQLiteOpenHelper.COLUMN_DATE,
			DaySQLiteOpenHelper.COLUMN_BEGIN_OR_END_PILLE_DATE,
			DaySQLiteOpenHelper.COLUMN_ARZTTERMIN,
			DaySQLiteOpenHelper.COLUMN_MONTH };

	public DayNoteDataSource(Context context) {
		dbHelper = new DaySQLiteOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public DayNote createDayNote(DayNote dayNote) {
		ContentValues values = new ContentValues();
		values.put(DaySQLiteOpenHelper.COLUMN_DAY_NOTE, dayNote.getNote());
		values.put(DaySQLiteOpenHelper.COLUMN_MENSTRUATION,
				dayNote.getMenstruation());
		values.put(DaySQLiteOpenHelper.COLUMN_STIMMUNGS, dayNote.getStimmungs());
		values.put(DaySQLiteOpenHelper.COLUMN_SYMPTOMS, dayNote.getSymptoms());
		values.put(DaySQLiteOpenHelper.COLUMN_DATE,
				DayNote.converDateToString(dayNote.getDate()));
		values.put(DaySQLiteOpenHelper.COLUMN_BEGIN_OR_END_PILLE_DATE,
				dayNote.getBegin_or_end_pille_date());
		values.put(DaySQLiteOpenHelper.COLUMN_ARZTTERMIN,
				dayNote.getArzttermin());
		values.put(DaySQLiteOpenHelper.COLUMN_MONTH, dayNote.getMonth());

		long insertId = database.insert(DaySQLiteOpenHelper.TABLE_DAY_NOTES,
				"", values);
		Cursor cursor = database.query(DaySQLiteOpenHelper.TABLE_DAY_NOTES,
				allColumns, DaySQLiteOpenHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		DayNote newDayNote = cursorToDayNote(cursor);
		cursor.close();
		return newDayNote;
	}

	public void saveOrupdateDayNote(DayNote dayNote) {
		ContentValues values = new ContentValues();
		values.put(DaySQLiteOpenHelper.COLUMN_DAY_NOTE, dayNote.getNote());
		values.put(DaySQLiteOpenHelper.COLUMN_MENSTRUATION,
				dayNote.getMenstruation());
		values.put(DaySQLiteOpenHelper.COLUMN_STIMMUNGS, dayNote.getStimmungs());
		values.put(DaySQLiteOpenHelper.COLUMN_SYMPTOMS, dayNote.getSymptoms());
		values.put(DaySQLiteOpenHelper.COLUMN_DATE,
				DayNote.converDateToString(dayNote.getDate()));
		values.put(DaySQLiteOpenHelper.COLUMN_BEGIN_OR_END_PILLE_DATE,
				dayNote.getBegin_or_end_pille_date());
		values.put(DaySQLiteOpenHelper.COLUMN_ARZTTERMIN,
				dayNote.getArzttermin());
		values.put(DaySQLiteOpenHelper.COLUMN_MONTH, dayNote.getMonth());

		int updated = database.update(DaySQLiteOpenHelper.TABLE_DAY_NOTES,
				values, null, null);

		if (updated == 0) {
			createDayNote(dayNote);
		}

	}

	public DayNote getDayNoteByDate(String date) {
		Cursor cursor = database.query(DaySQLiteOpenHelper.TABLE_DAY_NOTES,
				null, DaySQLiteOpenHelper.COLUMN_DATE + "='" + date + "'",
				null, null, null, null);

		cursor.moveToFirst();
		int count = cursor.getCount();
		if (count == 0) {
			return null;
		}

		DayNote dayNote = cursorToDayNote(cursor);
		cursor.close();
		return dayNote;
	}

	public List<DayNote> getDayNotesByMonth(int month) {
		String whereClose = DaySQLiteOpenHelper.COLUMN_MONTH + " = " + month;
		ArrayList<DayNote> list = new ArrayList<DayNote>();
		Cursor cursor = database.query(DaySQLiteOpenHelper.TABLE_DAY_NOTES,
				allColumns, whereClose, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			DayNote dayNote = cursorToDayNote(cursor);
			list.add(dayNote);
			cursor.moveToNext();
		}

		cursor.close();

		return list;

	}

	public void deleteDayNote(DayNote dayNote) {
		long id = dayNote.getId();
		Log.i(TAG, "Comment deleted with id: " + id);
		database.delete(DaySQLiteOpenHelper.TABLE_DAY_NOTES,
				DaySQLiteOpenHelper.COLUMN_ID + " = " + id, null);
	}

	public List<DayNote> getAllDayNotes() {
		List<DayNote> dayNotes = new ArrayList<DayNote>();

		Cursor cursor = database.query(DaySQLiteOpenHelper.TABLE_DAY_NOTES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			DayNote dayNote = cursorToDayNote(cursor);
			dayNotes.add(dayNote);
			cursor.moveToNext();
		}

		cursor.close();
		return dayNotes;
	}

	private DayNote cursorToDayNote(Cursor cursor) {
		DayNote dayNote = new DayNote();
		dayNote.setId(cursor.getLong(0));
		dayNote.setNote(cursor.getString(1));
		dayNote.setMenstruation(cursor.getString(2));
		dayNote.setSymptoms(cursor.getString(3));
		dayNote.setStimmungs(cursor.getString(4));
		dayNote.setDate(cursor.getString(5));
		dayNote.setBegin_or_end_pille_date(cursor.getString(6));
		dayNote.setArzttermin(cursor.getString(7));
		dayNote.setMonth(cursor.getInt(8));

		return dayNote;
	}
}
