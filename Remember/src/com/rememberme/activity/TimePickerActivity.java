package com.rememberme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.rememberme.R;

public class TimePickerActivity extends BaseActivity {

	public final static String NAME = TimePickerActivity.class.getSimpleName();
	private TimePicker mTimePicker;
	private final int response = 1;
	public final static String HOURS_OF_DAY = "hours_of_day";
	public final static String MINUTE = "minute";
	private Button okButton;
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
		super.onCreate(savedInstanceState);
		okButton = (Button) findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(HOURS_OF_DAY, mHoursOfDay);
				intent.putExtra(MINUTE, mMinute);
				setResult(response, intent);
				finish();
			}
		});
	}
}
