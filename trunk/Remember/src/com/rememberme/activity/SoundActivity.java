package com.rememberme.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.rememberme.R;
import com.rememberme.adapter.SoundsAdapter;

public class SoundActivity extends BaseActivity {

	public final static String[] SOUNDS = { "Disko Kitty", "Sonar",
			"Softbells", "Electro Boogie", "Fanfare" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sounds_layout);
		super.onCreate(savedInstanceState);
		SoundsAdapter adapter = new SoundsAdapter(SoundActivity.this,
				R.layout.sound_list_element, R.id.sound_name, SOUNDS);

		final ListView view = (ListView) findViewById(R.id.list_of_sounds);
		view.setAdapter(adapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View currentView,
					int position, long arg3) {
				View v = view.getChildAt(position);
				CheckedTextView cView = (CheckedTextView) v
						.findViewById(R.id.sound_name);
				if (cView.isSelected()) {
					cView.setSelected(false);
					cView.setCheckMarkDrawable(R.drawable.btn_check_off);
				} else {
					CheckedTextView cViewTemp;
					for (int i = 0; i < view.getChildCount(); i++) {
						v = view.getChildAt(i);
						cViewTemp = (CheckedTextView) v
								.findViewById(R.id.sound_name);
						cViewTemp.setSelected(false);
						cViewTemp
								.setCheckMarkDrawable(R.drawable.btn_check_off);
					}

					cView.setSelected(true);
					cView.setCheckMarkDrawable(R.drawable.btn_check_on);
				}
			}

		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
