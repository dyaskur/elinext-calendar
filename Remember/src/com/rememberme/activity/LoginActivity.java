package com.rememberme.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rememberme.R;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 4/17/12
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginActivity extends BaseActivity {
    private String password = "";
    private static int position = 0;
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final String FOUR = "4";
    private static final String FIVE = "5";
    private static final String SIX = "6";
    private static final String SEVEN = "7";
    private static final String EIGHT = "8";
    private static final String NINE = "9";
    private static final String ZERO = "0";
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main);
        super.onCreate(savedInstanceState);
        linearLayout = (LinearLayout) findViewById(R.id.edit_set);
        
        DayNoteDataSource dataSource = new DayNoteDataSource(this);
        dataSource.open();
        DayNote dayNote = new DayNote();
        dayNote.setDate("20-Mar-2012");
        dayNote.setNote("Ololo");
        
        dataSource.saveOrupdateDayNote(dayNote);
        List<DayNote> list = dataSource.getAllDayNotes();
        dataSource.close();
        
    }


    public void onClickDescription(View v) {
        EditText editText;
        switch (v.getId()) {
            case R.id.one:
                password += ONE;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(ONE);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.two:
                password += TWO;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(TWO);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.three:
                password += THREE;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(THREE);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.four:
                password += FOUR;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(FOUR);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.five:
                password += FIVE;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(FIVE);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.six:
                password += SIX;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(SIX);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.seven:
                password += SEVEN;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(SEVEN);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.eight:
                password += EIGHT;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(EIGHT);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.nine:
                password += NINE;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(NINE);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.zero:
                password += ZERO;
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText(ZERO);
                if (position < 3) {

                    linearLayout.getChildAt(++position).requestFocus();
                }
                break;
            case R.id.clear:
                editText = (EditText) linearLayout.getChildAt(position);
                editText.setText("");

                if (position != 0) {
                    password = password.substring(0, password.length() - 1);
                    linearLayout.getChildAt(--position).requestFocus();
                }
                break;
            default:
                break;
        }
        if (password.length() == 4) {
            if (password.equals("1111")){
                Toast.makeText(this, "OKAY", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this,MainCalendarActivity.class);
           		startActivity(intent);
                finish();
            }
            else
                Toast.makeText(this, "FUCK", Toast.LENGTH_LONG).show();
            for(int i=0;i<4;i++){
                editText=(EditText)linearLayout.getChildAt(i);
                editText.setText("");
            }
            password = "";
            position = 0;
            linearLayout.getChildAt(position).requestFocus();

        }
    }
}
