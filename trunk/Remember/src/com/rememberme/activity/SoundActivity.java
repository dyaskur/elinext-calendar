package com.rememberme.activity;


import android.os.Bundle;
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

		ListView view = (ListView) findViewById(R.id.list_of_sounds);
		view.setAdapter(adapter);
		
		
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
