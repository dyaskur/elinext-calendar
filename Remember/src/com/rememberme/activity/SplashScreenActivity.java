package com.rememberme.activity;


import android.content.Intent;
import android.os.Bundle;

import com.rememberme.R;

public class SplashScreenActivity extends BaseActivity {

	private static final int REQUEST_AUTH = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

	}

	@Override
	protected void onResume() {
		super.onResume();

		final Thread thread = new Thread(new Runnable() {
			public void run() {

				try {
					Thread.sleep(3000);
					requestAuth();
				} catch (InterruptedException e) {
					// Do nothing;
				}
			}
		});
		thread.start();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {

		// DO Nothing;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_AUTH){
			if(resultCode == RESULT_OK){
				startActivity(new Intent(this, MainCalendarActivity.class));
			} else {
				requestAuth();
			}
		}
	}

	protected void requestAuth() {
		Intent intent = new Intent(SplashScreenActivity.this,
				LoginActivity.class);
		startActivityForResult(intent, REQUEST_AUTH);
	}

}
