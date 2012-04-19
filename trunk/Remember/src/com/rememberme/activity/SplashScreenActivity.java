package com.rememberme.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.rememberme.R;

public class SplashScreenActivity extends BaseActivity {

	private static final int REQUEST_AUTH = 0;
	private SharedPreferences mPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

	}

	@Override
	protected void onResume() {
		super.onResume();

		mPreferences = getSharedPreferences(PasswordChangeActivity.PREF_AUTH,
				MODE_PRIVATE);

		final Thread thread = new Thread(new Runnable() {
			public void run() {

				try {
					Thread.sleep(3000);

					boolean isRequestAuth = mPreferences.getBoolean(
							PasswordChangeActivity.AUTH_REQURED, false);

					auth(isRequestAuth);

				} catch (InterruptedException e) {
					// Do nothing;
				}

			}

			

			private void auth(boolean isRequestAuth) {
				if (isRequestAuth) {
					requestAuth();
				} else {
					startActivity(new Intent(getApplicationContext(),
							MainCalendarActivity.class));
					finish();
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
		if (requestCode == REQUEST_AUTH) {
			if (resultCode == RESULT_OK) {
				startActivity(new Intent(this, MainCalendarActivity.class));
				finish();
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