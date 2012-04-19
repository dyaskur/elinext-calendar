package com.rememberme.activity;

import java.util.Date;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.rememberme.R;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;

public class DayActivity extends BaseActivity {
	public static final String PREFERENCES = "pref";
	public static final String DATE = "date";
	public static final String STIMMUNGS = "stimmungs";
	public static final String SYMPTOMES = "symptomes";
	public static final String MENSTRUATION = "menstruation";
	public static final String ARZTTERMIN = "arzttermin";
	public static final String BEGIN_ENDE = "begin_ende";

	public static String date = "";
	private TextView tvPilleneinnahme;
	private TextView tvArzttermin;
	public static DayNote dayNote;
	private CheckedTextView intim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_selected);

		TextView tvStimmung = (TextView) findViewById(R.id.stimmung);
		tvStimmung.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DayActivity.this,
						StimmungActivity.class);
				DayActivity.this.startActivity(intent);

			}
		});

		TextView tvsymptome = (TextView) findViewById(R.id.symptome);
		tvsymptome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DayActivity.this,
						SymptomeActivity.class);
				DayActivity.this.startActivity(intent);

			}
		});

		TextView tvMenstruation = (TextView) findViewById(R.id.menstruation);
		tvMenstruation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DayActivity.this,
						MenstruationActivity.class);
				DayActivity.this.startActivity(intent);

			}
		});

		tvPilleneinnahme = (TextView) findViewById(R.id.pilleneinnahme);
		tvPilleneinnahme.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DayActivity.this,
						PilleneinnahmeActivity.class);
				DayActivity.this.startActivity(intent);

			}
		});

		tvArzttermin = (TextView) findViewById(R.id.arzttermin);
		tvArzttermin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DayActivity.this,
						ArztterminActivity.class);
				DayActivity.this.startActivity(intent);

			}
		});

		DayNoteDataSource source = new DayNoteDataSource(this);
		source.open();
		dayNote = source.getDayNoteByDate(date);
		source.close();

		if (dayNote != null) {
			String noteText = dayNote.getNote();
			if (!noteText.equalsIgnoreCase("")) {
				EditText note = (EditText) findViewById(R.id.edittext);
				note.setText(noteText);

			}

			TextView pilleneinnahme = (TextView) findViewById(R.id.begin_time);
			if (dayNote.getBegin_or_end_pille_date() == null) {
				dayNote.setBegin_or_end_pille_date("");
			}

			if (!dayNote.getBegin_or_end_pille_date().equalsIgnoreCase("")) {
				pilleneinnahme.setText(pilleneinnahme.getText()
						+ dayNote.getBegin_or_end_pille_date());

			}

			if (dayNote.getArzttermin() == null) {
				dayNote.setArzttermin("");
			}

			TextView arzttermine = (TextView) findViewById(R.id.arzttermin_time);
			if (!dayNote.getArzttermin().equalsIgnoreCase("")) {
				arzttermine.setText(dayNote.getArzttermin());

			}

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES,
				0);

		String arz = sharedPreferences.getString(ARZTTERMIN, "");
		TextView textView1 = (TextView) findViewById(R.id.arzttermin_time);
		textView1.setText(arz);

		String begin_ende = sharedPreferences.getString(BEGIN_ENDE, "");
		TextView textView2 = (TextView) findViewById(R.id.begin_time);
		textView2.setText(begin_ende);

		intim = (CheckedTextView) findViewById(R.id.intime);
		intim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (intim.isChecked() == false) {
					intim.setChecked(true);
					intim.setCheckMarkDrawable(R.drawable.btn_check_on);

				} else {
					intim.setChecked(false);
					intim.setCheckMarkDrawable(R.drawable.btn_check_off);

				}

			}
		});

		if (dayNote != null) {
			if (dayNote.getIsIntim().equals("true")) {
				intim.setChecked(true);
				intim.setCheckMarkDrawable(R.drawable.btn_check_on);
			}
		}

	}

	@Override
	public void onBackPressed() {
		DayNote dayNote = new DayNote();

		if (date == null || date.equals("")) {
			dayNote.setDate(DayNote.converDateToString(new Date()));

		} else {
			dayNote.setDate(date);

		}

		EditText note = (EditText) findViewById(R.id.edittext);

		String noteStr = note.getText().toString();
		if (noteStr
				.equalsIgnoreCase(getString(R.string.heir_kannst_du_deine_notiz_eingeben_))) {
			noteStr = "";
		}

		dayNote.setNote(noteStr);

		SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES,
				0);
		String stim = sharedPreferences.getString(STIMMUNGS, "");
		if (!stim.equals("")) {
			dayNote.setStimmungs(stim);
		}

		String symp = sharedPreferences.getString(SYMPTOMES, "");
		if (!symp.equals("")) {
			dayNote.setSymptoms(symp);
		}

		String men = sharedPreferences.getString(MENSTRUATION, "");
		if (!men.equals("")) {
			dayNote.setMenstruation(men);
		}

		String arz = sharedPreferences.getString(ARZTTERMIN, "");
		if (!arz.equals("")) {
			dayNote.setArzttermin(arz);
		}

		String begin_ende = sharedPreferences.getString(BEGIN_ENDE, "");
		if (!begin_ende.equals("")) {
			dayNote.setBegin_or_end_pille_date(begin_ende);
		}

		if (intim.isChecked()) {
			dayNote.setIsIntim("true");

		} else {
			dayNote.setIsIntim("false");

		}

		dayNote.setNote(noteStr);
		DayNoteDataSource dataSource = new DayNoteDataSource(this);
		dataSource.open();
		dataSource.saveOrupdateDayNote(dayNote);
		dataSource.close();
		finish();
	}

}
