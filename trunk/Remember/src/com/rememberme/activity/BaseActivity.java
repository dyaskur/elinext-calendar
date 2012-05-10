package com.rememberme.activity;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;

import com.rememberme.R;
import com.rememberme.entity.DayNote;

public class BaseActivity extends Activity {

	private static final int REQUEST_AUTH = 0;
	static final String FIRST_LAUNCH = "if_first_launch";
	public final static String REMEMBERME = "remember_me";
	public final static String FIRST_DAY = "first_day";
	public final static String COUNT = "count";
	public static int firstDay = 0;
	private final static String[] months = { "January", "February", "March",
			"April", "May", "June", "July", "August", "September", "October",
			"November", "December" };
	private final static int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };

	public static DayNote dayNote = new DayNote();
	private static int daysWhenYearStarted;
	public static int theFirstDate = 0;
	private static AlarmManager alarm;
	private static long latIteraction = 0;
	private volatile static boolean perfomCheck = true;
	protected static volatile boolean showSplash = false;

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

	public static DayNote getDayNote() {
		return dayNote;
	}

	public static void setDayNote(DayNote dayNote) {
		BaseActivity.dayNote = dayNote;
	}

	public static int getFirstDate(Context context) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				REMEMBERME, MODE_WORLD_READABLE);

		if (sharedPreferences.contains(FIRST_DAY)) {
			String date = sharedPreferences.getString(FIRST_DAY, "");
			String[] dateInfo = date.split("-");
			int monthCount = 0;
			daysWhenYearStarted = 0;
			for (int i = 0; i < 12; i++) {
				if (months[i].contains(dateInfo[1])) {
					monthCount = i;
					break;
				}
			}

			for (int i = 0; i < monthCount; i++) {
				daysWhenYearStarted += daysOfMonth[i];
			}
			daysWhenYearStarted += Integer.parseInt(dateInfo[0]);
		}

		return daysWhenYearStarted;
	}

	public static void setCurrentCount(Context context, int dispose) {
		firstDay += dispose;
	}

	public static void initFirstDate(Context context) {
		firstDay = getFirstDate(context);
		theFirstDate = getFirstDate(context);
	}

	public static AlarmManager getAlarmInstance(Context context) {
		if (alarm == null) {
			alarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
		}
		return alarm;
	}

	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		latIteraction = System.currentTimeMillis();
	}

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// Log.i("TEST", "onCreate"+"|" + this.getClass().getName());
	// }
	//
	// @Override
	// protected void onStart() {
	// super.onStart();
	// Log.i("TEST", "onStart" +"|" + this.getClass().getName());
	// }
	//
	@Override
	protected void onResume() {
		super.onResume();
		if (getPerfomCheck()){
			perfomChaeck();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		setPerfomCheck(isApplicationBroughtToBackground());

	}

	@Override
	protected void onStop() {
		super.onStop();
		setPerfomCheck(isApplicationBroughtToBackground());
	}

	//
	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// Log.i("TEST", "onDestroy" +"|" + this.getClass().getName()) ;
	// }
	//
	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// Log.i("TEST", "onRestart" +"|" + this.getClass().getName());
	// }
	//
	// @Override
	// protected void onNewIntent(Intent intent) {
	// super.onNewIntent(intent);
	// Log.i("TEST", "onNewIntentnt" +"|" + this.getClass().getName());
	// }

	protected void perfomChaeck() {
		SharedPreferences preferences = getSharedPreferences(
				PasswordChangeActivity.PREF_AUTH, MODE_PRIVATE);
		boolean isRequestAuth = preferences.getBoolean(
				PasswordChangeActivity.AUTH_REQURED, false);

		boolean launch = getSharedPreferences(FIRST_LAUNCH, 0).getBoolean(
				FIRST_LAUNCH, false);

		if (launch == false) {
			SharedPreferences.Editor editor = getSharedPreferences(
					FIRST_LAUNCH, 0).edit();
			editor.putBoolean(FIRST_LAUNCH, true);

			editor.commit();
		} else {
			auth(isRequestAuth);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_AUTH) {
			if (resultCode == RESULT_OK) {
				if (showSplash){
					startActivity(new Intent(this, SplashScreenActivity.class));
				} else {
					startActivity(new Intent(this, MainCalendarActivity.class));
				}
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

	private boolean isApplicationBroughtToBackground() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(getPackageName())) {
				return true;
			} else {
				return tasks.get(0).numRunning == 0;
			}
		}

		return false;
	}
	
	public static synchronized void setPerfomCheck(boolean val){
		perfomCheck = val;
	}
	
	public static synchronized boolean getPerfomCheck(){
		return perfomCheck;
	}
}
