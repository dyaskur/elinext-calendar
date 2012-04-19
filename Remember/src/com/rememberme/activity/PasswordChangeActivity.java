package com.rememberme.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.rememberme.R;

public class PasswordChangeActivity extends Activity {
	public static final String AUTH_REQURED = "auth_requred";
	public static final String PREF_AUTH = "pref_auth";
	private static final int REQUEST_AUTH = 0;
	private ToggleButton toggleButton;
	private SharedPreferences mSharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_change_layout);
		toggleButton = (ToggleButton) findViewById(R.id.toggle_pass);

	}

	public void onToggleClick(View view) {

		boolean passWasSetted = mSharedPreferences.getBoolean(
				LoginActivity.PASS_WAS_SETTED, false);

		toggleButton.setChecked(mSharedPreferences.getBoolean(AUTH_REQURED,
				false));

		if (passWasSetted) {

			startActivityForResult(new Intent(this, LoginActivity.class),
					REQUEST_AUTH);
		} else {
			setPass();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		mSharedPreferences = getSharedPreferences(PREF_AUTH, MODE_PRIVATE);
		toggleButton.setChecked(mSharedPreferences.getBoolean(AUTH_REQURED,
				false));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_AUTH) {
			if (resultCode == RESULT_OK) {

				SharedPreferences.Editor editor = mSharedPreferences.edit();
				editor.putBoolean(AUTH_REQURED, !toggleButton.isChecked());
				editor.commit();
				finish();
			} else {
				requestAuth();
			}
		}
	}

	protected void requestAuth() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, REQUEST_AUTH);
	}

	private void setPass() {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		intent.putExtra(LoginActivity.MODE_AUTH, false);
		startActivity(intent);
		finish();
	}

}
