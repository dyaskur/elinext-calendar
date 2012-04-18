package com.rememberme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rememberme.R;

/**
 * Created with IntelliJ IDEA. User: User Date: 4/17/12 Time: 3:41 PM To change
 * this template use File | Settings | File Templates.
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

		// --------------------------------------------------------------this is a sample how to use sqlite db ------------------------------------------
		
		/*DayNoteDataSource dataSource = new DayNoteDataSource(this);
		dataSource.open();

		DayNote note = new DayNote();
		note.setNote("note");
		note.setMenstruation("men");
		note.setSymptoms("sympt/ololo/blabla");
		note.setStimmungs("stimmung/ololo/blabla");
		Date data = new Date();
		note.setDate(DayNote.converDateToString(data));
		note.setMonth(new Date().getMonth());
		dataSource.createDayNote(note);
		
		DayNote note2 = new DayNote();
		note2.setNote("note 2");
		note2.setMenstruation("men 2");
		note2.setSymptoms("sympt/ololo/blabla2");
		note2.setStimmungs("stimmung/ololo/blabla2");
		Date data2 = new Date();
		note2.setDate(DayNote.converDateToString(data2));
		note2.setMonth(2);
		dataSource.createDayNote(note2);

		List<DayNote> list = dataSource.getAllDayNotes();
		List<DayNote> m = dataSource.getDayNotesByMonth(3);
		List<DayNote> m2 = dataSource.getDayNotesByMonth(2);
		List<String> symp = list.get(0).getNormalizedSymptoms();
		dataSource.close();*/


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
			if (password.equals("1111")) {
				Toast.makeText(this, "OKAY", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(LoginActivity.this,
						MainCalendarActivity.class);
				startActivity(intent);
				finish();
			} else
				Toast.makeText(this, "FUCK", Toast.LENGTH_LONG).show();
			for (int i = 0; i < 4; i++) {
				editText = (EditText) linearLayout.getChildAt(i);
				editText.setText("");
			}
			password = "";
			position = 0;
			linearLayout.getChildAt(position).requestFocus();

		}
	}
}
