package com.rememberme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rememberme.R;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;

public class DayActivity extends BaseActivity{
private TextView tvPilleneinnahme;
private TextView tvArzttermin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_selected);
		TextView tvStimmung=(TextView)findViewById(R.id.stimmung);
		tvStimmung.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent  intent = new Intent(DayActivity.this, StimmungActivity.class);
				DayActivity.this.startActivity(intent);
				
			}
		});
		
		TextView tvsymptome=(TextView)findViewById(R.id.symptome);
		tvsymptome.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent  intent = new Intent(DayActivity.this, SymptomeActivity.class);
				DayActivity.this.startActivity(intent);
				
			}
		});
		TextView tvMenstruation=(TextView)findViewById(R.id.menstruation);
        tvMenstruation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent  intent = new Intent(DayActivity.this, MenstruationActivity.class);
                DayActivity.this.startActivity(intent);

            }
        });
		tvPilleneinnahme=(TextView)findViewById(R.id.pilleneinnahme);
        tvPilleneinnahme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent  intent = new Intent(DayActivity.this, PilleneinnahmeActivity.class);
                DayActivity.this.startActivity(intent);

            }
        });
    	
		tvArzttermin=(TextView)findViewById(R.id.arzttermin);
        tvArzttermin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent  intent = new Intent(DayActivity.this, ArztterminActivity.class);
                DayActivity.this.startActivity(intent);

            }
        });
        
        

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
    	DayNoteDataSource dataSource= new DayNoteDataSource(this);
    	dataSource.open();
    	if (dataSource.getDayNoteByDate("18-04-2012")!=null){
    		tvPilleneinnahme.setText("Begin/Ende Pilleneinnahme  "+dataSource.getDayNoteByDate("18-04-2012").getBegin_or_end_pille_date());
    	}
    	if (dataSource.getDayNoteByDate("18-04-2012")!=null){
    		tvArzttermin.setText("Arzttermin eintragen  "+dataSource.getDayNoteByDate("18-04-2012").getArzttermin());
    	}
    	dataSource.close();
	}

	//---WTF-- DO NOT WORKING
	//	public void onDayInfoItem (View v){
	//		Intent intent;
	//		switch (v.getId()) {
	//		case R.id.stimmung:
	//            intent = new Intent(DayActivity.this, StimmungActivity.class);
	//            DayActivity.this.startActivity(intent);
	//			break;
	//		case R.id.symptome:
	//            intent = new Intent(DayActivity.this, SymptomeActivity.class);
	//            DayActivity.this.startActivity(intent);
	//			break;
	//		case R.id.menstruation:
	//            intent = new Intent(DayActivity.this, SymptomeActivity.class);
	//            DayActivity.this.startActivity(intent);
	//			break;
	//
	//		case R.id.intime:
	//            intent = new Intent(DayActivity.this, SymptomeActivity.class);
	//            DayActivity.this.startActivity(intent);
	//			break;
	//
	//		case R.id.pilleneinnahme:
	//            intent = new Intent(DayActivity.this, SymptomeActivity.class);
	//            DayActivity.this.startActivity(intent);
	//			break;
	//		case R.id.arzttermin:
	//            intent = new Intent(DayActivity.this, SymptomeActivity.class);
	//            DayActivity.this.startActivity(intent);
	//			break;
	//		default:
	//			break;
	//		}
	//
	//	}
	
	@Override
    public void onBackPressed () {
    	DayNote dayNote=getDayNote();
    	EditText note=(EditText)findViewById(R.id.edittext);
    	dayNote.setNote(note.getText().toString());
    	DayNoteDataSource dataSource= new DayNoteDataSource(this);
    	dataSource.open();
    	dataSource.saveOrupdateDayNote(dayNote);
    	finish();
    }



}
