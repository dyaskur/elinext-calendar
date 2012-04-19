package com.rememberme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.rememberme.R;

public class FirstLaunch extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_launch);
		
		ImageView imageView = (ImageView) findViewById(R.id.weiter_btn);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FirstLaunch.this, MainCalendarActivity.class));
				finish();
				
			}
		});
		
	}
}
