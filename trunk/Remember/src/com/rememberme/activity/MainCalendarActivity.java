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
import android.widget.TextView;
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
	public static String day_month_year = "";
	private static final String dateTemplate = "MMMM yyyy";
	private TextView mNotes;
	private TextView mSympt;
	private TextView mStimung;
	private TextView mMenstruation;
	private TextView mTime;

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

		initCalendarData();
	}

	private void initCalendarData() {
		mNotes = (TextView) findViewById(R.id.notes);
		mSympt = (TextView) findViewById(R.id.symptomes);
		mStimung = (TextView) findViewById(R.id.stimung);
		mMenstruation = (TextView) findViewById(R.id.minstruation);
		mTime = (TextView) findViewById(R.id.time);

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
			startActivity(new Intent(this, PasswordChangeActivity.class));
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

	// "•";

	private String checkIfEqualsNull(String str) {
		if (str == null) {
			return "";
		} else {
			return str;

		}
	}

	public void setSelectedDayNote(DayNote dayNote) {
		if (dayNote != null) {
			mNotes.setText(getString(R.string.notiz_)
					+ checkIfEqualsNull(dayNote.getNote()));
			String sypm = "";
			String spacer_sym = "                     ";
			for (String i : dayNote.getNormalizedSymptoms()) {
				sypm += "• " + i + "\n" + spacer_sym;
			}

			mSympt.setText(getString(R.string.symptome_) + sypm);

			String stim = "";
			String spacer_stim = "                    ";
			for (String i : dayNote.getNormalizedStimmungs()) {
				stim += "• " + i + "\n" + spacer_stim;

			}

			mStimung.setText(getString(R.string.stimmung_) + stim);
			mMenstruation.setText(getString(R.string.menstruation_)
					+ checkIfEqualsNull(dayNote.getMenstruation()));
			mTime.setText(getString(R.string.arzttermin_)
					+ checkIfEqualsNull(dayNote.getArzttermin()));
		} else {
			setClear();
			dayNote = new DayNote();
			dayNote.setDate(day_month_year);
			BaseActivity.setDayNote(dayNote);
		}



	}


	private void setClear() {
		mNotes.setText(getString(R.string.notiz_));
		mSympt.setText(getString(R.string.symptome_));
		mStimung.setText(getString(R.string.stimmung_));
		mMenstruation.setText(getString(R.string.menstruation_));
		mTime.setText(getString(R.string.arzttermin_));
		
	}
}
