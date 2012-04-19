package com.rememberme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rememberme.R;
import com.rememberme.adapter.ItemAdapter;

public class SymptomeActivity extends BaseActivity{
    public final static String[] ITEMS = {"Spannungsgefuhl der Bruste","Gewichtszunahme","Schmierblutung","Zwischenblutung","Unreine Haut","Vollegefuhl", "Erbrechen",
            "Durchfall", "Unterleibskrampfe", "Kopfschmerzen", "Ruckenschmerzen", "Gliederschmerzen",
            "Nackenschmerzen"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.stimmung_layout);
        super.onCreate(savedInstanceState);

        ItemAdapter adapter = new ItemAdapter(SymptomeActivity.this,
                R.layout.item, R.id.item_name, ITEMS);

        ListView view = (ListView) findViewById(R.id.list_stimmung);
        view.setAdapter(adapter);
    }
}
