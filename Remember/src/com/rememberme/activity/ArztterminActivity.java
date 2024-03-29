package com.rememberme.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.rememberme.R;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;

import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: Luxor-XP Date: 19.04.12 Time: 1:04 To
 * change this template use File | Settings | File Templates.
 */
public class ArztterminActivity extends BaseActivity {
	private TimePicker tp;
	private ToggleButton tb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arzttermin_layout);
		TextView titleText = (TextView) findViewById(R.id.date);
		tp = (TimePicker) findViewById(R.id.timepicker_az);
		tp.setIs24HourView(true);
		if (DayActivity.date == null || DayActivity.date.equals("")) {
			titleText.setText(DayNote.converDateToString(new Date()));

		} else {
			titleText.setText(DayActivity.date);

		}
		tb= (ToggleButton)findViewById(R.id.toggleButton);
        DayNoteDataSource source = new DayNoteDataSource(this);
        source.open();
        dayNote = source.getDayNoteByDate(DayActivity.date);
        source.close();
		if(dayNote!=null){
			if(dayNote.getArzttermin()!=null && !dayNote.getArzttermin().equals("") && !dayNote.getArzttermin().equals("-")){
				tb.setChecked(true);
				String[] split = dayNote.getArzttermin().split(":");
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
			if(tp.getCurrentMinute()<10){
				str+="0" + tp.getCurrentMinute().toString();
			} else {
				str+= tp.getCurrentMinute().toString();
			}	

			SharedPreferences.Editor editor = getSharedPreferences(
					DayActivity.PREFERENCES, 0).edit();
			editor.putString(DayActivity.ARZTTERMIN, str);
			editor.commit();
		} else {
			String string = "-";
			SharedPreferences.Editor editor = getSharedPreferences(
					DayActivity.PREFERENCES, 0).edit();
			editor.putString(DayActivity.ARZTTERMIN, string);
			editor.commit();
		}

		finish();
	}
}
