package com.rememberme.activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	private List<String> symptome = new LinkedList<String>();
	private List<String> selectedItems = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.stimmung_layout);
		super.onCreate(savedInstanceState);

		ItemAdapter adapter = new AdapterSymp(SymptomeActivity.this,
				R.layout.item, R.id.item_name, ITEMS);

		view = (ListView) findViewById(R.id.list_stimmung);
		view.setAdapter(adapter);

		DayNote dayNote = DayActivity.dayNote;
		if (dayNote != null) {
			selectedItems = dayNote.getNormalizedStimmungs();
		}

	}

	private class AdapterSymp extends ItemAdapter {

		public AdapterSymp(Context context, int resource,
				int textViewResourceId, String[] items) {
			super(context, resource, textViewResourceId, items);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater
					.from(SymptomeActivity.this);
			View textView = (View) inflater.inflate(R.layout.item, null);
			CheckedTextView checkedTextView = (CheckedTextView) textView
					.findViewById(R.id.item_name);
			checkedTextView.setText(ITEMS[position]);
			if (selectedItems.contains(checkedTextView.getText())) {
				checkedTextView.setChecked(true);
				checkedTextView.setCheckMarkDrawable(R.drawable.btn_check_on);
			}
			return textView;
		}
	}

	@Override
	public void onBackPressed() {
		CheckedTextView cView;
		View v;
		for (int i = 0; i < view.getChildCount(); i++) {
			v = view.getChildAt(i);
			cView = (CheckedTextView) v.findViewById(R.id.item_name);

			if (cView.isSelected()) {
				symptome.add(cView.getText().toString());
			}
		}

		String symptomeStr = "";
		for (int i = 0; i < symptome.size(); i++) {
			if (i == symptome.size() - 1) {
				symptomeStr += ITEMS[i];

			} else {
				symptomeStr += ITEMS[i] + "/";

			}

		}

		SharedPreferences.Editor editor = getSharedPreferences(
				DayActivity.PREFERENCES, 0).edit();
		editor.putString(DayActivity.SYMPTOMES, symptomeStr);
		editor.commit();

		finish();
	}
}
