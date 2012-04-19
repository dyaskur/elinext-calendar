package com.rememberme.activity;

import android.app.Activity;
import android.view.View;
import android.widget.CheckedTextView;

import com.rememberme.R;
import com.rememberme.entity.DayNote;

public class BaseActivity extends Activity {

	public final static String REMEMBERME = "remember_me";


public static DayNote dayNote  = new DayNote();

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
	
	

}
