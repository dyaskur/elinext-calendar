package com.rememberme.activity;

import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.rememberme.R;

public class BaseActivity extends Activity {

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

}
