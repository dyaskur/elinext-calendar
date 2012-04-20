package com.rememberme.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.rememberme.R;
import com.rememberme.adapter.ItemAdapter;
import com.rememberme.entity.DayNote;

public class SymptomeActivity extends BaseActivity {
	public final static String[] ITEMS = { "Spannungsgefuhl der Bruste",
			"Gewichtszunahme", "Schmierblutung", "Zwischenblutung",
			"Unreine Haut", "Vollegefuhl", "Erbrechen", "Durchfall",
			"Unterleibskrampfe", "Kopfschmerzen", "Ruckenschmerzen",
			"Gliederschmerzen", "Nackenschmerzen" };
	private ListView view;

	private List<String> selectedItems = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private List<String> symptome = new ArrayList<String>();
	private HashMap<String, Boolean> map = new HashMap<String, Boolean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.stimmung_layout);
		super.onCreate(savedInstanceState);

		adapter = new AdapterSymp(SymptomeActivity.this, R.layout.item,
				R.id.item_name, ITEMS);

		view = (ListView) findViewById(R.id.list_stimmung);
		view.setAdapter(adapter);

		DayNote dayNote = DayActivity.dayNote;
		if (dayNote != null) {
			selectedItems = dayNote.getNormalizedSymptoms();
		}

		for (String i : ITEMS) {
			map.put(i, false);
		}

		symptome.addAll(selectedItems);
		for (String i : symptome) {
			map.put(i, true);
		}

	}

	private class AdapterSymp extends ArrayAdapter<String> {

		public AdapterSymp(Context context, int resource,
				int textViewResourceId, String[] items) {
			super(context, resource, textViewResourceId, items);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(SymptomeActivity.this);
				convertView = (View) inflater.inflate(R.layout.item, null);

			}

			final CheckedTextView checkedTextView = (CheckedTextView) convertView
					.findViewById(R.id.item_name);
			checkedTextView.setText(ITEMS[position]);

			if (symptome.contains(checkedTextView.getText())) {
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
					if (map.get(textView.getText())) {
						textView.setSelected(false);
						textView.setCheckMarkDrawable(R.drawable.btn_check_off);
						symptome.remove(textView.getText());
						map.put((String) textView.getText(), false);

					} else {
						map.put((String) textView.getText(), true);
						symptome.add((String) textView.getText());
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

		String symptomeStr = "-";
		for (int i = 0; i < symptome.size(); i++) {
			if (symptomeStr.equals("-")) {
				symptomeStr = "";
			}

			if (i == symptome.size() - 1) {
				symptomeStr += symptome.get(i);

			} else {
				symptomeStr += symptome.get(i) + "/";

			}

		}

		SharedPreferences.Editor editor = getSharedPreferences(
				DayActivity.PREFERENCES, 0).edit();
		editor.putString(DayActivity.SYMPTOMES, symptomeStr);
		editor.commit();

		finish();
	}
}
