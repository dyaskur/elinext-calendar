package com.rememberme.activity;

import java.security.acl.LastOwnerException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.rememberme.R;

public class SplashScreenActivity extends Activity {

	private static final int REQUEST_AUTH = 0;
	private static final String FIRST_LAUNCH = "first_launch_app";
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
					Thread.sleep(2000);
					
					boolean launch = getSharedPreferences(FIRST_LAUNCH, 0)
							.getBoolean(FIRST_LAUNCH, true);

					if (launch == false) {
						startActivity(new Intent(getApplicationContext(), MainCalendarActivity.class));
					} else {
						SharedPreferences.Editor editor = getSharedPreferences(
								FIRST_LAUNCH, 0).edit();
						editor.putBoolean(FIRST_LAUNCH, false);
						SharedPreferences sharedPreferences1 = getSharedPreferences(
								BaseActivity.REMEMBERME, MODE_WORLD_WRITEABLE);
						SharedPreferences.Editor editor1 = sharedPreferences1
								.edit();
						editor1.putString(AlarmActivity.ALARM_STATUS, "off");
						editor1.commit();
						editor.commit();
						startActivity(new Intent(getApplicationContext(), FirstLaunch.class));
					}				
					
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
