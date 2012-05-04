package com.rememberme.activity;

import java.util.Date;

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
			if (dayNote.getBegin_or_end_pille_date() != null
					&& !dayNote.getBegin_or_end_pille_date().equals("")
					&& !dayNote.getBegin_or_end_pille_date().equals("-")) {
				tb.setChecked(true);
				String[] split = dayNote.getBegin_or_end_pille_date().split(":");
				tp.setCurrentHour(Integer.parseInt(split[0]));
				tp.setCurrentMinute(Integer.parseInt(split[1]));
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

		} else {
			String str = "-";
			SharedPreferences.Editor editor = getSharedPreferences(
					DayActivity.PREFERENCES, 0).edit();
			editor.putString(DayActivity.BEGIN_ENDE, str);
			editor.commit();
		}

		finish();
	}


}
