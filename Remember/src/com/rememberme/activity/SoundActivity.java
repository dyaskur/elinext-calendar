package com.rememberme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
		SoundsAdapter adapter = new SoundsAdapter(SoundActivity.this,
				R.layout.sound_list_element, R.id.sound_name, SOUNDS);

		ListView view = (ListView) findViewById(R.id.list_of_sounds);
		view.setAdapter(adapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
				setResult(position, intent);
				finish();

			}
		});
		
		super.onCreate(savedInstanceState);
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
