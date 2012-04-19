package com.rememberme.activity;
import java.util.LinkedList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.rememberme.R;
import com.rememberme.adapter.ItemAdapter;

public class StimmungActivity extends BaseActivity {
    public final static String[] ITEMS = { "glucklich", "traurig",
            "depriment", "euphorisch", "relaxed", "angespannt", "gereizt", "gelassen", "hungrig", "flirty" };
	private List<String> stimmung=new LinkedList<String>();
	private ListView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.stimmung_layout);
        super.onCreate(savedInstanceState);

        ItemAdapter adapter = new ItemAdapter(StimmungActivity.this,
                R.layout.item, R.id.item_name, ITEMS);

        view = (ListView) findViewById(R.id.list_stimmung);
        view.setAdapter(adapter);
        
//        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

//            @Override
//            public void onItemClick(AdapterView<?> arg0, View view1,
//                                    int position, long arg3) {
//				View v = view.getChildAt(position);
//				CheckedTextView cView = (CheckedTextView) v
//						.findViewById(R.id.menstr_item_name);
//                if (cView.isSelected())
//                {
//                    cView.setSelected(false);
//                    cView.setCheckMarkDrawable (R.drawable.btn_check_off);
//                }
//                else
//                {
//                    cView.setSelected(true);
//                    cView.setCheckMarkDrawable (R.drawable.btn_check_on);
//                }
//
//
//
//            }
//        });

    }
    
    
	@Override
    public void onBackPressed () {
    	CheckedTextView cView;
    	View v;
		for (int i = 0; i < view.getChildCount(); i++) {
			v = view.getChildAt(i);
			cView = (CheckedTextView) v.findViewById(R.id.item_name);

			if (cView.isSelected()) {
				stimmung.add(cView.getText().toString());
			}
		}

		String stimmungStr = "";
		for (int i = 0; i < stimmung.size(); i++) {
			if (i == stimmung.size()) {
				stimmungStr += i;

			} else {
				stimmungStr += i + "/";

			}

		}

		SharedPreferences.Editor editor = getSharedPreferences(
				DayActivity.PREFERENCES, 0).edit();
		editor.putString(DayActivity.STIMMUNGS, stimmungStr);
		editor.commit();
    	
    	finish();
    }

}
