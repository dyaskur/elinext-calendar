package com.rememberme.activity;

import android.os.Bundle;
import android.view.View;

import com.rememberme.R;

public class DialogActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.dialog_layout);
		
		super.onCreate(savedInstanceState);
	}

	public void OnCancelButtonClick(View view) {

		if (view.getId() == R.id.cancel_btn) {

			AlarmActivity.stopPlaySound();
			//AlarmActivity.turnOffButton(DialogActivity.this);
			finish();
		}

	}
}
