package com.rememberme.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckedTextView;

import com.rememberme.R;
import com.rememberme.entity.DayNote;

public class BaseActivity extends Activity {

	public final static String REMEMBERME = "remember_me";
	public final static String FIRST_DAY = "first_day";
	public static int firstDay = 0;
	private final static String[] months = { "January", "February", "March",
			"April", "May", "June", "July", "August", "September", "October",
			"November", "December" };
	private final static int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };

	public static DayNote dayNote = new DayNote();
	private static int daysWhenYearStarted;
	public static int theFirstDate = 0;
	private static AlarmManager alarm;

	public void toggle(View v) {
		CheckedTextView cView = (CheckedTextView) v
				.findViewById(R.id.item_name);
		if (cView.isSelected()) {
			cView.setSelected(false);
			cView.setCheckMarkDrawable(R.drawable.btn_check_off);
		} else {
			cView.setSelected(true);
			cView.setCheckMarkDrawable(R.drawable.btn_check_on);
		}
	}

	public void toggleIntime(View v) {
		CheckedTextView cView = (CheckedTextView) v.findViewById(R.id.intime);
		if (cView.isSelected()) {
			cView.setSelected(false);
			cView.setCheckMarkDrawable(R.drawable.btn_check_off);
		} else {
			cView.setSelected(true);
			cView.setCheckMarkDrawable(R.drawable.btn_check_on);
		}
	}

	public static DayNote getDayNote() {
		return dayNote;
	}

	public static void setDayNote(DayNote dayNote) {
		BaseActivity.dayNote = dayNote;
	}

	public static int getFirstDate(Context context) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				REMEMBERME, MODE_WORLD_READABLE);

		if (sharedPreferences.contains(FIRST_DAY)) {
			String date = sharedPreferences.getString(FIRST_DAY, "");
			String[] dateInfo = date.split("-");
			int monthCount = 0;
			daysWhenYearStarted = 0;
			for (int i = 0; i < 12; i++) {
				if (months[i].contains(dateInfo[1])) {
					monthCount = i;
					break;
				}
			}

			for (int i = 0; i < monthCount; i++) {
				daysWhenYearStarted += daysOfMonth[i];
			}
			daysWhenYearStarted += Integer.parseInt(dateInfo[0]);
		}

		return daysWhenYearStarted;
	}

	public static void setCurrentCount(Context context, int dispose) {
		firstDay += dispose;
	}

	public static void initFirstDate(Context context) {
		firstDay = getFirstDate(context);
		theFirstDate = getFirstDate(context);
	}
	
	public static AlarmManager getAlarmInstance(Context context){
		if(alarm==null){
			alarm=(AlarmManager) context.getSystemService(ALARM_SERVICE);			
		} 
		return alarm;
	}
}
