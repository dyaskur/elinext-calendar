package com.rememberme.broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.rememberme.R;
import com.rememberme.activity.AlarmActivity;
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
		Toast.makeText(context, R.string.alarm, Toast.LENGTH_SHORT).show();
		AlarmActivity.playSound((Integer) intent
				.getSerializableExtra(SoundActivity.CURRENT_SOUND), context);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AlarmActivity.stopPlaySound();
							}
						});
		builder.create();

	}
}