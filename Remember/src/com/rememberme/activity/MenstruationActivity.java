package com.rememberme.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import com.rememberme.R;
import com.rememberme.adapter.ItemAdapter;
import com.rememberme.adapter.MenstrItemAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Luxor-XP
 * Date: 18.04.12
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class MenstruationActivity extends BaseActivity {
    public final static String[] ITEMS = {"leicht", "mittel",
            "stark"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.stimmung_layout);
        super.onCreate(savedInstanceState);

        MenstrItemAdapter adapter = new MenstrItemAdapter(MenstruationActivity.this,
                R.layout.menstr_item, R.id.menstr_item_name, ITEMS);

        final ListView view = (ListView) findViewById(R.id.list_stimmung);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                View v = view.getChildAt(i);
                CheckedTextView cView = (CheckedTextView) v.findViewById(R.id.menstr_item_name);
                if (cView.isSelected()) {
                    cView.setSelected(false);
                    cView.setCheckMarkDrawable(R.drawable.btn_check_off);
                } else {
                    v = view.getChildAt(0);
                    CheckedTextView cViewTemp = (CheckedTextView) v.findViewById(R.id.menstr_item_name);
                    cViewTemp.setSelected(false);
                    cViewTemp.setCheckMarkDrawable(R.drawable.btn_check_off);
                    v = view.getChildAt(1);
                    cViewTemp = (CheckedTextView) v.findViewById(R.id.menstr_item_name);
                    cViewTemp.setSelected(false);
                    cViewTemp.setCheckMarkDrawable(R.drawable.btn_check_off);
                    v = view.getChildAt(2);
                    cViewTemp = (CheckedTextView) v.findViewById(R.id.menstr_item_name);
                    cViewTemp.setSelected(false);
                    cViewTemp.setCheckMarkDrawable(R.drawable.btn_check_off);
                    cView.setSelected(true);
                    cView.setCheckMarkDrawable(R.drawable.btn_check_on);
                }
            }
        });
    }
}
