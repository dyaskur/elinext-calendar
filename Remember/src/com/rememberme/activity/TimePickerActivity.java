package com.rememberme.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.rememberme.R;

public class TimePickerActivity extends BaseActivity {

	public final static String NAME = TimePickerActivity.class.getSimpleName();
	private TimePicker mTimePicker;
	private final int response = 1;
	public final static String HOURS_OF_DAY = "hours_of_day";
	public final static String MINUTE = "minute";
	private int mHoursOfDay;
	private int mMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.time_picker_layout);

		mTimePicker = (TimePicker) findViewById(R.id.timepicker);
		mTimePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

				mHoursOfDay = hourOfDay;
				mMinute = minute;
			}
		});
		if (mHoursOfDay == 0) {
			mHoursOfDay = mTimePicker.getCurrentHour();
			mMinute = mTimePicker.getCurrentMinute();
		}
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onBackPressed() {
		saveSharedAlarmTime();
		super.onBackPressed();
	}

	private void saveSharedAlarmTime() {
		SharedPreferences sharedPreferences = getSharedPreferences(REMEMBERME,
				MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(HOURS_OF_DAY, mHoursOfDay);
		editor.putInt(MINUTE, mMinute);
		editor.commit();
	}
}
