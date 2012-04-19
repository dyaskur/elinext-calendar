package com.rememberme.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.rememberme.R;
import com.rememberme.adapter.ItemAdapter;

public class StimmungActivity extends BaseActivity {
    public final static String[] ITEMS = { "glucklich", "traurig",
            "depriment", "euphorisch", "relaxed", "angespannt", "gereizt", "gelassen", "hungrig", "flirty" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.stimmung_layout);
        super.onCreate(savedInstanceState);

        ItemAdapter adapter = new ItemAdapter(StimmungActivity.this,
                R.layout.item, R.id.item_name, ITEMS);

        ListView view = (ListView) findViewById(R.id.list_stimmung);
        view.setAdapter(adapter);
//        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View v,
//                                    int position, long arg3) {
//                CheckedTextView cView = (CheckedTextView) v.findViewById(R.id.item_name);
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

//    public void toggle(View v) {
//        CheckedTextView cView = (CheckedTextView) v.findViewById(R.id.item_name);
//        if (cView.isSelected()) {
//            cView.setSelected(false);
//            cView.setCheckMarkDrawable (R.drawable.btn_check_off);
//        }
//        else {
//            cView.setSelected(true);
//            cView.setCheckMarkDrawable (R.drawable.btn_check_on);
//        }
//    }


}
