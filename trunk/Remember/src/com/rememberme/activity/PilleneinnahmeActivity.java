package com.rememberme.activity;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.rememberme.R;
import com.rememberme.broadcast.PilleneinnahmeReciver;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;

/**
 * Created with IntelliJ IDEA. User: Luxor-XP Date: 19.04.12 Time: 1:06 To
 * change this template use File | Settings | File Templates.
 */
public class PilleneinnahmeActivity extends BaseActivity {
	private static MediaPlayer mMediaPlayer;
	private TimePicker tp;
	private ToggleButton tb;
	private PendingIntent sender;
	private AlarmManager am;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pilleneinnahme_layout);
		TextView titleText = (TextView) findViewById(R.id.date);
		tp = (TimePicker) findViewById(R.id.timepicker);
		tp.setIs24HourView(true);
		if (DayActivity.date == null || DayActivity.date.equals("")) {
			titleText.setText(DayNote.converDateToString(new Date()));

		} else {
			titleText.setText(DayActivity.date);

		}
		tb = (ToggleButton) findViewById(R.id.toggleButton);
        DayNoteDataSource source = new DayNoteDataSource(this);
        source.open();
        dayNote = source.getDayNoteByDate(DayActivity.date);
        source.close();
		if (dayNote != null) {
			if (dayNote.getArzttermin() != null
					&& !dayNote.getArzttermin().equals("")
					&& !dayNote.getArzttermin().equals("-")) {
				tb.setChecked(true);
			} else {
				tb.setChecked(false);
			}
		}
	}

	@Override
	public void onBackPressed() {
		ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton);
		if (button.isChecked()) {

			String str = tp.getCurrentHour().toString() + ":";
			if (tp.getCurrentMinute() < 10) {
				str += "0" + tp.getCurrentMinute().toString();
			} else {
				str += tp.getCurrentMinute().toString();
			}

			SharedPreferences.Editor editor = getSharedPreferences(
					DayActivity.PREFERENCES, 0).edit();
			editor.putString(DayActivity.BEGIN_ENDE, str);
			editor.commit();
			Intent intent = new Intent(PilleneinnahmeActivity.this,
					PilleneinnahmeReciver.class);

			sender = PendingIntent.getBroadcast(PilleneinnahmeActivity.this, 0,
					intent, 0);

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			int mCurrentHours = calendar.get(Calendar.HOUR_OF_DAY);
			int mCurrentMinutes = calendar.get(Calendar.MINUTE);

			int mAlarmTime = ((tp.getCurrentHour() - mCurrentHours) * 60 + (tp
					.getCurrentMinute() - mCurrentMinutes)) * 60;

			calendar.add(Calendar.SECOND, mAlarmTime);

			// Schedule the alarm!
			am = BaseActivity.getAlarmInstance(this);
			am.cancel(sender);
			sender = PendingIntent.getBroadcast(PilleneinnahmeActivity.this, 0,
					intent, 0);
			am.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);

			// Tell the user about what we did.

		} else {
			String str = "-";
			SharedPreferences.Editor editor = getSharedPreferences(
					DayActivity.PREFERENCES, 0).edit();
			editor.putString(DayActivity.BEGIN_ENDE, str);
			editor.commit();
		}

		finish();
	}

	public static void showNotification(Context context) {
		Intent notificationIntent = new Intent(context, Notification.class);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, "Notify",
				System.currentTimeMillis());
		notification.setLatestEventInfo(context, "RememberMe",
				"Pille einnehmen",
				PendingIntent.getActivity(context, 0, notificationIntent, 0));

		notification.vibrate = new long[] { 100, 200, 100, 500 };
		notificationManager.notify(0, notification);

		notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE
				| Notification.FLAG_AUTO_CANCEL;
		mMediaPlayer = MediaPlayer.create(context, R.raw.soft_bells);
		mMediaPlayer.start();
		// notificationManager.cancel(0);
		/*
		 * if (mMediaPlayer != null) { mMediaPlayer.release(); mMediaPlayer =
		 * null; }
		 */
	}

}
