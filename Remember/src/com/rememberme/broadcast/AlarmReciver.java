package com.rememberme.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.rememberme.R;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.;

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm
 * that should occur once.
 * <p>
 * When the alarm goes off, we show a <i>Toast</i>, a quick message.
 */
public class AlarmReciver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, R.string.alarm, Toast.LENGTH_SHORT).show();
		
	}
}