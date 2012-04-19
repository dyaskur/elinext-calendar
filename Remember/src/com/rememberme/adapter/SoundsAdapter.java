package com.rememberme.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rememberme.R;

public class SoundsAdapter extends ArrayAdapter<String> {

	private String[] mSounds;
	private LayoutInflater mInflater;
	private TextView mSoundname;

	public SoundsAdapter(Context context, int resource, int textViewResourceId,
			String[] sounds) {
		super(context, resource, textViewResourceId, sounds);

		mSounds = sounds;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewGroup currentView = (ViewGroup) mInflater.inflate(
				R.layout.sound_list_element, null);
		mSoundname = (TextView) currentView.findViewById(R.id.sound_name);
		mSoundname.setText(mSounds[position]);
		return currentView;
	}

}
