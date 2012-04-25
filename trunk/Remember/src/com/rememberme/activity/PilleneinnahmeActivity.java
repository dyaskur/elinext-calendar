package com.rememberme.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.rememberme.R;
import com.rememberme.entity.DayNote;

import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: Luxor-XP Date: 19.04.12 Time: 1:06 To
 * change this template use File | Settings | File Templates.
 */
public class PilleneinnahmeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pilleneinnahme_layout);
        TextView titleText = (TextView) findViewById(R.id.date);
        if (DayActivity.date == null || DayActivity.date.equals("")) {
            titleText.setText(DayNote.converDateToString(new Date()));

        } else {
            titleText.setText(DayActivity.date);

        }
	}

	@Override
	public void onBackPressed() {
		ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton);
		if (button.isChecked()) {
			TimePicker tp = (TimePicker) findViewById(R.id.timepicker);
			String str = tp.getCurrentHour().toString() + ":"
					+ tp.getCurrentMinute().toString();

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
