package com.rememberme.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TimePicker;

import com.rememberme.R;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Luxor-XP
 * Date: 19.04.12
 * Time: 1:04
 * To change this template use File | Settings | File Templates.
 */
public class ArztterminActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arzttermin_layout);
    }
    
//	@Override
//    public void onBackPressed () {
//    	CheckedTextView cView;
//    	View v;
//    	for (int i=0;i<view.getChildCount();i++){
//			v = view.getChildAt(i);
//			cView = (CheckedTextView) v.findViewById(R.id.item_name);
//
//    		if (cView.isSelected()){
//    			menstruation=cView.getText().toString();
//    		}
//    	}
//    	
//    	DayNote dayNote=new DayNote();
//    	dayNote.setMenstruation(menstruation);
//    	DayNoteDataSource dataSource= new DayNoteDataSource(this);
//    	dataSource.open();
//    	dataSource.saveOrupdateDayNote(dayNote);
//    	finish();
//    }
    
	@Override
    public void onBackPressed () {  	
    	DayNote dayNote=getDayNote();
    	TimePicker tp=(TimePicker)findViewById(R.id.timepicker_az);
    	dayNote.setArzttermin(tp.getCurrentHour().toString()+":"+tp.getCurrentMinute().toString());
    	DayNoteDataSource dataSource= new DayNoteDataSource(this);
    	dataSource.open();
    	dataSource.saveOrupdateDayNote(dayNote);
    	dataSource.close();
    	finish();
    }
}
