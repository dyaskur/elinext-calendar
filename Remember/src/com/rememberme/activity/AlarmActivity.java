package com.rememberme.activity;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.rememberme.R;
import com.rememberme.broadcast.AlarmReciver;

/**
 * Example of scheduling one-shot and repeating alarms. See {@link OneShotAlarm}
 * for the code run when the one-shot alarm goes off, and {@link RepeatingAlarm}
 * for the code run when the repeating alarm goes off. <h4>Demo</h4>
 * App/Service/Alarm Controller
 * 
 * <h4>Source files</h4>
 * <table class="LinkTable">
 * <tr>
 * <td class="LinkColumn">src/com.example.android.apis/app/AlarmController.java</td>
 * <td class="DescrColumn">The activity that lets you schedule alarms</td>
 * </tr>
 * <tr>
 * <td class="LinkColumn">src/com.example.android.apis/app/OneShotAlarm.java</td>
 * <td class="DescrColumn">This is an intent receiver that executes when the
 * one-shot alarm goes off</td>
 * </tr>
 * <tr>
 * <td class="LinkColumn">src/com.example.android.apis/app/RepeatingAlarm.java</td>
 * <td class="DescrColumn">This is an intent receiver that executes when the
 * repeating alarm goes off</td>
 * </tr>
 * <tr>
 * <td class="LinkColumn">/res/any/layout/alarm_controller.xml</td>
 * <td class="DescrColumn">Defines contents of the screen</td>
 * </tr>
 * </table>
 */
public class AlarmActivity extends BaseActivity {
	private Toast mToast;
	private ToggleButton mToggleButton;
	private TextView mTimeValue;
	private TextView mSoundValue;
	private int requestCode = 1;
	private Integer hoursOfDay;
	private Integer minute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alarm_layout);

		// Watch for button clicks.
		/*
		 * Button button = (Button) findViewById(R.id.one_shot);
		 * button.setOnClickListener(mOneShotListener);
		 */
		mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		mToggleButton.setOnCheckedChangeListener(OnToogleButtonClick());
		mTimeValue = (TextView) findViewById(R.id.time_value);
		mTimeValue.setOnClickListener(OnTimeButtonClick());
		mSoundValue = (TextView) findViewById(R.id.sound_value);
		mSoundValue.setOnClickListener(OnSoundButtonClick());

	}

	private OnCheckedChangeListener OnToogleButtonClick() {
		return new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {

					Intent intent = new Intent(AlarmActivity.this,
							AlarmReciver.class);
					PendingIntent sender = PendingIntent.getBroadcast(
							AlarmActivity.this, 0, intent, 0);

					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(System.currentTimeMillis());
					int mCurrentHours = calendar.get(Calendar.HOUR_OF_DAY);
					int mCurrentMinutes = calendar.get(Calendar.MINUTE);

					int mAlarmTime = ((hoursOfDay - mCurrentHours) * 60 + (minute - mCurrentMinutes)) * 60;

					calendar.add(Calendar.SECOND, mAlarmTime);

					// Schedule the alarm!
					AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
					am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
							sender);

					// Tell the user about what we did.
					if (mToast != null) {
						mToast.cancel();
					}
					mToast = Toast.makeText(AlarmActivity.this,
							R.string.alarm_scheduled, Toast.LENGTH_LONG);
					mToast.show();
				} else {

				}

			}
		};
	}

	private OnClickListener OnSoundButtonClick() {
		return new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(AlarmActivity.this, "Sound pressed",
						Toast.LENGTH_SHORT).show();

			}
		};
	}

	private OnClickListener OnTimeButtonClick() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AlarmActivity.this,
						TimePickerActivity.class);
				startActivityForResult(intent, requestCode);

			}
		};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		hoursOfDay = (Integer) data
				.getSerializableExtra(TimePickerActivity.HOURS_OF_DAY);
		minute = (Integer) data.getSerializableExtra(TimePickerActivity.MINUTE);
		String fixedMinute = "";
		if (minute < 10) {
			fixedMinute = "0" + minute;
		} else {
			fixedMinute = "" + minute;
		}
		String alarmTime = hoursOfDay + " : " + fixedMinute;
		mTimeValue.setText(alarmTime);

		super.onActivityResult(requestCode, resultCode, data);
	}
}