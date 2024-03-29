package com.rememberme.broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.rememberme.R;
import com.rememberme.activity.AlarmActivity;
import com.rememberme.activity.BaseActivity;
import com.rememberme.activity.DialogActivity;
import com.rememberme.activity.SoundActivity;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.;

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm
 * that should occur once.
 * <p>
 * When the alarm goes off, we show a <i>Toast</i>, a quick message.
 */
// -----------------------------------------
public class AlarmReciver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				BaseActivity.REMEMBERME, context.MODE_WORLD_WRITEABLE);

		AlarmActivity.playSound(
				sharedPreferences.getInt(SoundActivity.CURRENT_SOUND, 1),
				context);
		

	}
}