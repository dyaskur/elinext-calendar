package com.rememberme.activity;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.rememberme.R;
import com.rememberme.adapter.ItemAdapter;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;

public class SymptomeActivity extends BaseActivity{
    public final static String[] ITEMS = {"Spannungsgefuhl der Bruste","Gewichtszunahme","Schmierblutung","Zwischenblutung","Unreine Haut","Vollegefuhl", "Erbrechen",
            "Durchfall", "Unterleibskrampfe", "Kopfschmerzen", "Ruckenschmerzen", "Gliederschmerzen",
            "Nackenschmerzen"};
    private ListView view;
    private List<String> symptome = new LinkedList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.stimmung_layout);
        super.onCreate(savedInstanceState);

        ItemAdapter adapter = new ItemAdapter(SymptomeActivity.this,
                R.layout.item, R.id.item_name, ITEMS);

        view = (ListView) findViewById(R.id.list_stimmung);
        view.setAdapter(adapter);
    }
    
    
	@Override
    public void onBackPressed () {
    	CheckedTextView cView;
    	View v;
    	for (int i=0;i<view.getChildCount();i++){
			v = view.getChildAt(i);
			cView = (CheckedTextView) v.findViewById(R.id.item_name);

    		if (cView.isSelected()){
    			symptome.add(cView.getText().toString());
    		}
    	}
    	
    	DayNote dayNote=getDayNote();
    	dayNote.setListSymptoms(symptome);
    	DayNoteDataSource dataSource= new DayNoteDataSource(this);
    	dataSource.open();
    	dataSource.saveOrupdateDayNote(dayNote);
    	dataSource.close();
    	finish();
    }
}
