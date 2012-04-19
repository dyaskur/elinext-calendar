package com.rememberme.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.rememberme.R;

public class InfoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.info_layout);
		ImageView imageView = (ImageView) findViewById(R.id.web_site);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://pille-fuer-mich.de/";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);

			}
		});

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {

		// TODO
		super.onResume();
	};

}
