package com.rememberme.activity;

import java.util.Date;
import java.util.HashMap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

import android.widget.TextView;
import com.rememberme.R;
import com.rememberme.adapter.MenstrItemAdapter;
import com.rememberme.entity.DayNote;

/**
 * Created with IntelliJ IDEA. User: Luxor-XP Date: 18.04.12 Time: 22:44 To
 * change this template use File | Settings | File Templates.
 */
public class MenstruationActivity extends BaseActivity {
	public final static String[] ITEMS = { "leicht", "mittel", "stark" };
	private ListView view;
	private String menstruation = "-";
	public static HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.stimmung_layout);
		super.onCreate(savedInstanceState);

        TextView titleText = (TextView) findViewById(R.id.date);
        if (DayActivity.date == null || DayActivity.date.equals("")) {
            titleText.setText(DayNote.converDateToString(new Date()));

        } else {
            titleText.setText(DayActivity.date);

        }
		
		DayNote dayNote = DayActivity.dayNote;
		String selected = "";
		if(dayNote != null) {
			selected = dayNote.getMenstruation();
		}
		
		for(String i: ITEMS) {
			if(i.equalsIgnoreCase(selected)) {
				map.put(i, true);
				
			} else {
				map.put(i, false);
				
			}
		}

			
		MenstrItemAdapter adapter = new MenstrItemAdapter(
				MenstruationActivity.this, R.layout.menstr_item,
				R.id.menstr_item_name, ITEMS, selected);
		
		

		view = (ListView) findViewById(R.id.list_stimmung);
		view.setAdapter(adapter);
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view1,
					int i, long l) {
				View v = view.getChildAt(i);
				CheckedTextView cView = (CheckedTextView) v
						.findViewById(R.id.menstr_item_name);
				if (map.get(cView.getText())) {
					cView.setSelected(false);
					map.put((String) cView.getText(), false);
					cView.setCheckMarkDrawable(R.drawable.btn_check_off);

				} else {
					CheckedTextView cViewTemp;
					for (int count = 0; count < view.getChildCount(); count++) {
						v = view.getChildAt(count);
						cViewTemp = (CheckedTextView) v
								.findViewById(R.id.menstr_item_name);
						cViewTemp.setSelected(false);
						map.put((String) cViewTemp.getText(), false);
						cViewTemp
								.setCheckMarkDrawable(R.drawable.btn_check_off);
					}
					
					cView.setSelected(true);
					map.put((String) cView.getText(), true);
					cView.setCheckMarkDrawable(R.drawable.btn_check_on);

				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		CheckedTextView cView;
		View v;
		for (int i = 0; i < view.getChildCount(); i++) {
			v = view.getChildAt(i);
			cView = (CheckedTextView) v.findViewById(R.id.menstr_item_name);

			if (cView.isSelected()) {
				menstruation = cView.getText().toString();
			}
		}

		SharedPreferences.Editor editor = getSharedPreferences(
				DayActivity.PREFERENCES, 0).edit();
		editor.putString(DayActivity.MENSTRUATION, menstruation);
		editor.commit();
		
		finish();
	}
}
