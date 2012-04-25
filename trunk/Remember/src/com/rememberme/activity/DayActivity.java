package com.rememberme.activity;

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

import java.util.Date;

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
    private TextView pilleneinnahme;
    private TextView arzttermine;
    private boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_selected);

        pilleneinnahme = (TextView) findViewById(R.id.begin_time);
        arzttermine = (TextView) findViewById(R.id.arzttermin_time);

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
        // if (dayNote!=null && dayNote.getBegin_or_end_pille_date() != null){
        // beginTime = (TextView)findViewById(R.id.begin_time);
        // //beginTime.setTextColor(Color.BLACK);
        // if(!dayNote.getBegin_or_end_pille_date().equals("-")){
        // String tmp = dayNote.getBegin_or_end_pille_date();
        // beginTime.setTextColor(Color.BLACK);
        // beginTime.setText(tmp);
        // }
        // }
        //
        // if (dayNote!=null && dayNote.getArzttermin() != null){
        // arzentime = (TextView)findViewById(R.id.arzttermin_time);
        // //arzentime.setTextColor(Color.BLACK);
        // if(!dayNote.getArzttermin().equals("-")){
        // String tmp=dayNote.getArzttermin();
        // arzentime.setTextColor(Color.BLACK);
        // arzentime.setText(tmp);
        // }
        // }

        TextView titleText = (TextView) findViewById(R.id.date);
        if (date == null || date.equals("")) {
            titleText.setText(DayNote.converDateToString(new Date()));

        } else {
            titleText.setText(date);

        }

        if (dayNote != null) {
            String noteText = dayNote.getNote();
            if (!noteText.equalsIgnoreCase("")) {
                EditText note = (EditText) findViewById(R.id.edittext);
                note.setText(noteText);
                // note.setFocusable(false);

            }

//            if (dayNote.getBegin_or_end_pille_date() == null) {
//                dayNote.setBegin_or_end_pille_date("");
//            }
//
//            if (!dayNote.getBegin_or_end_pille_date().equalsIgnoreCase("-")) {
//                pilleneinnahme.setText(dayNote.getBegin_or_end_pille_date());
//                pilleneinnahme.invalidate();
//
//            }
//
//            if (dayNote.getArzttermin() == null) {
//                dayNote.setArzttermin("");
//            }
//
//            if (!dayNote.getArzttermin().equalsIgnoreCase("-")) {
//                arzttermine.setText(dayNote.getArzttermin());
//
//            }

        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (state) {

            if (dayNote != null) {
                if (dayNote.getBegin_or_end_pille_date() == null) {
                    dayNote.setBegin_or_end_pille_date("");
                }

                if (!dayNote.getBegin_or_end_pille_date().equalsIgnoreCase("-")) {
                    pilleneinnahme.setText(dayNote.getBegin_or_end_pille_date());
                    pilleneinnahme.invalidate();

                }

                if (dayNote.getArzttermin() == null) {
                    dayNote.setArzttermin("");
                }

                if (!dayNote.getArzttermin().equalsIgnoreCase("-")) {
                    arzttermine.setText(dayNote.getArzttermin());

                }

            }
            state = false;

        } else {

            SharedPreferences sharedPreferences = getSharedPreferences(
                    PREFERENCES, 0);

            String arz = sharedPreferences.getString(ARZTTERMIN, "");
            TextView textView1 = (TextView) findViewById(R.id.arzttermin_time);
            if (arz.equals("-")) {
                textView1.setText("");

            } else {
                if (arz.equals("") && dayNote!=null && dayNote.getArzttermin()!=null && !dayNote.getArzttermin().equals(""))
                    textView1.setText(dayNote.getArzttermin());
                else
                    textView1.setText(arz);

            }

            String begin_ende = sharedPreferences.getString(BEGIN_ENDE, "");
            TextView textView2 = (TextView) findViewById(R.id.begin_time);
            if (begin_ende.equals("-")) {
                textView2.setText("");

            } else {
                if (begin_ende.equals("") && dayNote!=null && dayNote.getBegin_or_end_pille_date()!=null && !dayNote.getBegin_or_end_pille_date().equals(""))
                    textView2.setText(dayNote.getBegin_or_end_pille_date());
                else
                    textView2.setText(begin_ende);

            }
            saveToDB();
        }
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
        saveToDB();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES,
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(DATE);
        editor.remove(STIMMUNGS);
        editor.remove(SYMPTOMES);
        editor.remove(MENSTRUATION);
        editor.remove(ARZTTERMIN);
        editor.remove(BEGIN_ENDE);
        editor.commit();
        finish();
    }

    private void saveToDB() {
        if (dayNote == null) {
            dayNote = new DayNote();
        }

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
            if (stim.equals("-")) {
                dayNote.setStimmungs(null);
            } else {
                dayNote.setStimmungs(stim);
            }

        }

        String symp = sharedPreferences.getString(SYMPTOMES, "");
        if (!symp.equals("")) {
            if (symp.equals("-")) {
                dayNote.setSymptoms(null);
            } else {
                dayNote.setSymptoms(symp);
            }
        }

        String men = sharedPreferences.getString(MENSTRUATION, "");
        if (!men.equals("")) {
            if (men.equals("-")) {
                dayNote.setMenstruation(null);
            } else {
                dayNote.setMenstruation(men);
            }
        }

        String arz = sharedPreferences.getString(ARZTTERMIN, "");
        if (!arz.equals("")) {
            if (arz.equals("-")) {
                dayNote.setArzttermin(null);
            } else {
                dayNote.setArzttermin(arz);
            }
        }

        String begin_ende = sharedPreferences.getString(BEGIN_ENDE, "");
        if (!begin_ende.equals("")) {
            if(begin_ende.equals("-")){
                dayNote.setBegin_or_end_pille_date(null);
            } else {
                dayNote.setBegin_or_end_pille_date(begin_ende);
            }
        }

        if (intim.isChecked()) {
            dayNote.setIsIntim("true");

        } else {
            dayNote.setIsIntim("false");

        }

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        /*
//        * public static final String DATE = "date"; public static final String
//        * STIMMUNGS = "stimmungs"; public static final String SYMPTOMES =
//        * "symptomes"; public static final String MENSTRUATION =
//        * "menstruation"; public static final String ARZTTERMIN = "arzttermin";
//        * public static final String BEGIN_ENDE = "begin_ende";
//        */
//        editor.remove(DATE);
//        editor.remove(STIMMUNGS);
//        editor.remove(SYMPTOMES);
//        editor.remove(MENSTRUATION);
//        editor.remove(ARZTTERMIN);
//        editor.remove(BEGIN_ENDE);
//        editor.commit();

        dayNote.setNote(noteStr);
        DayNoteDataSource dataSource = new DayNoteDataSource(this);
        dataSource.open();
        dataSource.saveOrupdateDayNote(dayNote);
        dataSource.close();
        if (dayNote.getBegin_or_end_pille_date() != null
                && !dayNote.getBegin_or_end_pille_date().equals("")) {
            SharedPreferences sharedPreferences2 = getSharedPreferences(
                    REMEMBERME, MODE_WORLD_WRITEABLE);
            if (!sharedPreferences2.contains(FIRST_DAY)) {
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putString(FIRST_DAY, dayNote.getDate());
                editor2.commit();
            }
        }
    }

}
