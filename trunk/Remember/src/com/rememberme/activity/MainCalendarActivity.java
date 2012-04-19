package com.rememberme.activity;

import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rememberme.R;
import com.rememberme.adapter.GridCellAdapter;
import com.rememberme.entity.DayNote;
import com.rememberme.utils.DayNoteLoadAction;

public class MainCalendarActivity extends BaseActivity implements
		OnClickListener, DayNoteLoadAction {
	private static final String tag = "SimpleCalendarViewActivity";

	private Button currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	private LinearLayout mInfo;
	private int month, year;
	private static final String dateTemplate = "MMMM yyyy";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_layout);

		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
				+ year);
		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (Button) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) this.findViewById(R.id.calendar);

		mInfo = (LinearLayout) this.findViewById(R.id.info);
		mInfo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(MainCalendarActivity.this,
						DayActivity.class);
				startActivity(intent);

			}
		});

		// Initialised
		adapter = new GridCellAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year, this);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	/**
	 * 
	 * @param month
	 * @param year
	 */
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year, this);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	public void onClick(View v) {
		if (v == prevMonth) {
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
		if (v == nextMonth) {
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}

	}

	public void onButtonSetClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.info_btn:
			intent = new Intent(MainCalendarActivity.this, InfoActivity.class);
			startActivity(intent);
			break;
		case R.id.key_btn:
			Toast.makeText(MainCalendarActivity.this, "Key button pressed!",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.alarm_btn:
			intent = new Intent(MainCalendarActivity.this, AlarmActivity.class);
			startActivity(intent);
			break;
		case R.id.plus_btn:
			Toast.makeText(MainCalendarActivity.this, "Plus button pressed!",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		Log.d(tag, "Destroying View ...");
		super.onDestroy();
	}

	@Override
	public void setSelectedDayNote(DayNote dayNote) {
		// TODO Auto-generated method stub

	}

}
