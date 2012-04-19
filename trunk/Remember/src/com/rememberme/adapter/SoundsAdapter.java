package com.rememberme.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.UserDictionary.Words;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.rememberme.R;
import com.rememberme.activity.BaseActivity;
import com.rememberme.activity.SoundActivity;

public class SoundsAdapter extends ArrayAdapter<String> {

	private String[] mSounds;
	private Context mContext;
	private LayoutInflater mInflater;
	private TextView mSoundname;
	private CheckedTextView checkTextBox;
	private int currentSountIndex;

	public SoundsAdapter(Context context, int resource, int textViewResourceId,
			String[] sounds) {
		super(context, resource, textViewResourceId, sounds);
		mContext = context;
		mSounds = sounds;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewGroup currentView = (ViewGroup) mInflater.inflate(
				R.layout.sound_list_element, null);

		checkTextBox = (CheckedTextView) currentView
				.findViewById(R.id.sound_name);

		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				BaseActivity.REMEMBERME, Context.MODE_WORLD_READABLE);
		if (sharedPreferences.contains(SoundActivity.CURRENT_SOUND)) {

			currentSountIndex = sharedPreferences.getInt(
					SoundActivity.CURRENT_SOUND, 1);

			if (position == currentSountIndex) {
				checkTextBox.setSelected(true);
				checkTextBox.setCheckMarkDrawable(R.drawable.btn_check_on);
			}
		}

		mSoundname = (TextView) currentView.findViewById(R.id.sound_name);
		mSoundname.setText(mSounds[position]);
		return currentView;
	}

}
