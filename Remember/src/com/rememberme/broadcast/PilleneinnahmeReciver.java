package com.rememberme.broadcast;

import com.rememberme.activity.PilleneinnahmeActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PilleneinnahmeReciver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		PilleneinnahmeActivity.showNotification(context);
	}
}