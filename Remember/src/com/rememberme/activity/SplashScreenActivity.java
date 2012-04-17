package com.rememberme.activity;

import android.content.Intent;
import android.os.Bundle;

import com.rememberme.R;

public class SplashScreenActivity extends BaseActivity {

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
					Intent intent = new Intent(SplashScreenActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
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

}
