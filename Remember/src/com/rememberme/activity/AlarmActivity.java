package com.rememberme.activity;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
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

	private final static String TAG = "AlarmActivity";
	private Toast mToast;
	private ToggleButton mToggleButton;
	private TextView mTimeValue;
	private TextView mSoundValue;
	private TextView mBigClock;
	private final static int TIME_RESULT = 10;
	private static MediaPlayer mMediaPlayer;
	private int hoursOfDay;
	private LinearLayout mTime;
	private LinearLayout mSound;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;

	private int minute;
	private int mCurrentSound;
	public final static String ALARM_STATUS = "alarm_status";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alarm_layout);

		mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		mToggleButton.setOnCheckedChangeListener(OnToogleButtonClick());
		mTimeValue = (TextView) findViewById(R.id.time_value);
		mTime = (LinearLayout) findViewById(R.id.time);
		mTime.setOnClickListener(OnTimeButtonClick());
		mSoundValue = (TextView) findViewById(R.id.sound_value);
		mSound = (LinearLayout) findViewById(R.id.sound);
		mSound.setOnClickListener(OnSoundButtonClick());
		mBigClock = (TextView) findViewById(R.id.big_clock);

		sharedPreferences = getSharedPreferences(REMEMBERME,
				MODE_WORLD_WRITEABLE);
		editor = sharedPreferences.edit();

		if (sharedPreferences.contains(TimePickerActivity.HOURS_OF_DAY)
				&& sharedPreferences.contains(SoundActivity.CURRENT_SOUND)) {
			mToggleButton.setEnabled(true);

		} else {
			mToggleButton.setEnabled(false);
		}

		if (sharedPreferences.getString(ALARM_STATUS, "").equals("on")) {
			mToggleButton.setChecked(true);
		}

	}

	private OnCheckedChangeListener OnToogleButtonClick() {
		return new OnCheckedChangeListener() {

			private AlarmManager am;
			private PendingIntent sender;

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					if (sharedPreferences.contains(ALARM_STATUS)) {
						if (sharedPreferences.getString(ALARM_STATUS, "")
								.equals("off")) {

							Intent intent = new Intent(AlarmActivity.this,
									AlarmReciver.class);

							intent.putExtra(SoundActivity.CURRENT_SOUND,
									mCurrentSound);
							sender = PendingIntent.getBroadcast(
									AlarmActivity.this, 0, intent, 0);

							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(System.currentTimeMillis());
							int mCurrentHours = calendar
									.get(Calendar.HOUR_OF_DAY);
							int mCurrentMinutes = calendar.get(Calendar.MINUTE);

							int mAlarmTime = ((hoursOfDay - mCurrentHours) * 60 + (minute - mCurrentMinutes)) * 60;

							calendar.add(Calendar.SECOND, mAlarmTime);

							// Schedule the alarm!
							am = (AlarmManager) getSystemService(ALARM_SERVICE);
							am.set(AlarmManager.RTC_WAKEUP,
									calendar.getTimeInMillis(), sender);

							// Tell the user about what we did.
							if (mToast != null) {
								mToast.cancel();
							}
							mToast = Toast
									.makeText(AlarmActivity.this,
											R.string.alarm_scheduled,
											Toast.LENGTH_LONG);
							mToast.show();
							editor.putString(ALARM_STATUS, "on");
							editor.commit();
						} else {
							editor.putString(ALARM_STATUS, "on");
							editor.commit();
						}
					}

				} else {
					if (am != null) {
						am.cancel(sender);
						AlarmActivity.stopPlaySound();

					}
					editor.putString(ALARM_STATUS, "off");
					editor.commit();
				}

			}
		};
	}

	private OnClickListener OnSoundButtonClick() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AlarmActivity.this,
						SoundActivity.class);
				startActivity(intent);

			}
		};
	}

	private OnClickListener OnTimeButtonClick() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AlarmActivity.this,
						TimePickerActivity.class);
				startActivity(intent);

			}
		};
	}

	@Override
	protected void onResume() {
		SharedPreferences sharedPreferences = getSharedPreferences(REMEMBERME,
				MODE_WORLD_READABLE);

		if (sharedPreferences.contains(SoundActivity.CURRENT_SOUND)) {
			mCurrentSound = sharedPreferences.getInt(
					SoundActivity.CURRENT_SOUND, 1);
			mSoundValue.setText(SoundActivity.SOUNDS[mCurrentSound]);
		}
		if (sharedPreferences.contains(TimePickerActivity.HOURS_OF_DAY)) {
			hoursOfDay = sharedPreferences.getInt(
					TimePickerActivity.HOURS_OF_DAY, 1);
			minute = sharedPreferences.getInt(TimePickerActivity.MINUTE, 1);
			String fixedMinute = "";
			if (minute < 10) {
				fixedMinute = "0" + minute;
			} else {
				fixedMinute = "" + minute;
			}
			String alarmTime = hoursOfDay + " : " + fixedMinute;
			mTimeValue.setText(alarmTime);
			mBigClock.setText(alarmTime);

		}
		if (sharedPreferences.contains(TimePickerActivity.HOURS_OF_DAY)
				&& sharedPreferences.contains(SoundActivity.CURRENT_SOUND)) {
			mToggleButton.setEnabled(true);
		} else {
			mToggleButton.setEnabled(false);
		}
		if (sharedPreferences.getString(ALARM_STATUS, "").equals("on")) {
			mToggleButton.setChecked(true);
		}

		super.onResume();
	}

	public static void playSound(Integer soundId, Context context) {
		try {
			switch (soundId) {
			case 0:
				mMediaPlayer = MediaPlayer.create(context, R.raw.disco_kitty);
				break;
			case 1:
				mMediaPlayer = MediaPlayer.create(context, R.raw.sonar);
				break;
			case 2:
				mMediaPlayer = MediaPlayer.create(context, R.raw.soft_bells);
				break;
			case 3:
				mMediaPlayer = MediaPlayer
						.create(context, R.raw.electro_boogie);
				break;
			case 4:
				mMediaPlayer = MediaPlayer.create(context, R.raw.fanfare);
				break;
			default:
				break;
			}

			mMediaPlayer.start();

		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}

	}

	public static void stopPlaySound() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}