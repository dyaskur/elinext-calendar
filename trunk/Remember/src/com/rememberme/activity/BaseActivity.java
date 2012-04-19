package com.rememberme.activity;


import android.app.Activity;
import android.view.View;
import android.widget.CheckedTextView;

import com.rememberme.R;

public class BaseActivity extends Activity {

	public void toggleSound(View v) {
		CheckedTextView cView = (CheckedTextView) v
				.findViewById(R.id.sound_name);
		if (cView.isSelected()) {
			cView.setSelected(false);
			cView.setCheckMarkDrawable(R.drawable.btn_check_off);
		} else {
			cView.setSelected(true);
			cView.setCheckMarkDrawable(R.drawable.btn_check_on);
		}
	}
	
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
            cView.setCheckMarkDrawable (R.drawable.btn_check_off);
        }
        else {
            cView.setSelected(true);
            cView.setCheckMarkDrawable (R.drawable.btn_check_on);
        }
    }

}
