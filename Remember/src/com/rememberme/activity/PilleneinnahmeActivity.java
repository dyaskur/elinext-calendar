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
		tb= (ToggleButton)findViewById(R.id.toggleButton);
		DayNote dayNote=DayActivity.dayNote;
		if(dayNote!=null){
			if(dayNote.getArzttermin()!=null && !dayNote.getArzttermin().equals("") && !dayNote.getArzttermin().equals("-")){
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
			if(tp.getCurrentMinute()<10){
				str+="0" + tp.getCurrentMinute().toString();
			} else {
				str+= tp.getCurrentMinute().toString();
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
