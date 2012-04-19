package com.rememberme.activity;

import com.rememberme.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class PasswordChangeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_change_layout);
	}
	
	public void onToggleClick(View view) {
		ToggleButton button = (ToggleButton)view;
	
		
	}
}
