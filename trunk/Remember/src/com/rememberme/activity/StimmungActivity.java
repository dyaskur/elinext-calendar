package com.rememberme.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.rememberme.R;
import com.rememberme.entity.DayNote;

public class StimmungActivity extends BaseActivity {
	public final static String[] ITEMS = { "glucklich", "traurig", "depriment",
			"euphorisch", "relaxed", "angespannt", "gereizt", "gelassen",
			"hungrig", "flirty" };
	
	private ListView view;
	private List<String> selectedItems = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private List<String> newSelectedList = new ArrayList<String>();
	private HashMap<String, Boolean> map = new HashMap<String, Boolean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.stimmung_layout);
		super.onCreate(savedInstanceState);

		adapter = new AdapterStim(StimmungActivity.this, R.layout.item,
				R.id.item_name, ITEMS);

		DayNote dayNote = DayActivity.dayNote;
		if (dayNote != null) {
			selectedItems = dayNote.getNormalizedStimmungs();
		}

		for(String i: ITEMS) {
			map.put(i, false);
		}
		
		newSelectedList.addAll(selectedItems);
		for(String i: newSelectedList) {
			map.put(i, true);
		}

		view = (ListView) findViewById(R.id.list_stimmung);
		view.setAdapter(adapter);

	}

	private class AdapterStim extends ArrayAdapter<String> {

		public AdapterStim(Context context, int resource,
				int textViewResourceId, String[] objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(StimmungActivity.this);
				convertView = (View) inflater.inflate(R.layout.item, null);

			}

			final CheckedTextView checkedTextView = (CheckedTextView) convertView
					.findViewById(R.id.item_name);
			checkedTextView.setText(ITEMS[position]);

			if (newSelectedList.contains(checkedTextView.getText())) {
				checkedTextView.setChecked(true);
				checkedTextView.setCheckMarkDrawable(R.drawable.btn_check_on);
			} else {
				checkedTextView.setChecked(false);
				checkedTextView.setCheckMarkDrawable(R.drawable.btn_check_off);
			}

			checkedTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v1) {
					CheckedTextView textView = (CheckedTextView) v1;
					if(map.get(textView.getText())) {
						textView.setSelected(false);
						textView.setCheckMarkDrawable(R.drawable.btn_check_off);
						newSelectedList.remove(textView.getText());
						map.put((String) textView.getText(), false);
						
					} else {
						map.put((String) textView.getText(), true);
						newSelectedList.add((String) textView.getText());
						textView.setSelected(true);
						textView.setCheckMarkDrawable(R.drawable.btn_check_on);
						
					}

				}
			});

			return convertView;
		}

	}

	@Override
	public void onBackPressed() {

		String stimmungStr = "-";
		for (int i = 0; i < newSelectedList.size(); i++) {
			if (stimmungStr.equals("-")) {
				stimmungStr = "";
			}

			if (i == newSelectedList.size() - 1) {
				stimmungStr += newSelectedList.get(i);

			} else {
				stimmungStr += newSelectedList.get(i) + "/";

			}

		}

		SharedPreferences.Editor editor = getSharedPreferences(
				DayActivity.PREFERENCES, 0).edit();
		editor.putString(DayActivity.STIMMUNGS, stimmungStr);
		editor.commit();

		finish();
	}

}
