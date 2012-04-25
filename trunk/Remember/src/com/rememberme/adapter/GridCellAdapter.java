package com.rememberme.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rememberme.R;
import com.rememberme.activity.BaseActivity;
import com.rememberme.activity.MainCalendarActivity;
import com.rememberme.entity.DayNote;
import com.rememberme.sqlite.DayNoteDataSource;
import com.rememberme.utils.DayNoteLoadAction;

public class GridCellAdapter extends BaseAdapter implements OnClickListener {
	private static final String tag = "GridCellAdapter";
	private final Context _context;

	private final List<String> list;
	private static final int DAY_OFFSET = 1;
	private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
			"Wed", "Thu", "Fri", "Sat" };
	private final String[] months = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };
	private final int[] daysOfMonthUnusual = { 31, 29, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };
	private final int month, year;
	private int daysInMonth, prevMonthDays;
	private int currentDayOfMonth;
	private int currentWeekDay;
	private Button gridcell;
	private Button selectedDayMonthYearButton;
	private TextView num_events_per_day;
	private final HashMap eventsPerMonthMap;
	private View layout;
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd-mmm-yyyy");
	private DayNoteDataSource mDayNoteDataSource;
	private DayNoteLoadAction mAction;
	private View lastSelectedCell;
	private ImageView mPille;
	private ImageView mPilleStart;
	private ImageView mIntim;
	private ImageView mPlus;
	private ImageView mNotes;
	private ImageView mPeriode;

	private static int counter = 0;

	// Days in Current Month
	public GridCellAdapter(Context context, int textViewResourceId, int month,
			int year, DayNoteLoadAction action) {
		super();
		this._context = context;
		this.list = new ArrayList<String>();
		this.month = month;
		this.year = year;
		mAction = action;
		mDayNoteDataSource = new DayNoteDataSource(context);

		Log.d(tag, "==> Passed in Date FOR Month: " + month + " " + "Year: "
				+ year);
		Calendar calendar = Calendar.getInstance();
		setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
		setCurrentWeekDay(getDay(calendar));
		Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
		Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
		Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

		// Print Month
		printMonth(month, year);

		// Find Number of Events
		eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
	}

	private String getMonthAsString(int i) {
		return months[i];
	}

	private String getWeekDayAsString(int i) {
		return weekdays[i];
	}

	private int getNumberOfDaysOfMonth(int i, int year) {
		if (year % 4 == 0) {
			return daysOfMonthUnusual[i];
		} else {
			return daysOfMonth[i];
		}
	}

	public String getItem(int position) {
		return list.get(position);
	}

	public int getCount() {
		return list.size();
	}

	/**
	 * Prints Month
	 * 
	 * @param mm
	 * @param yy
	 */
	private void printMonth(int mm, int yy) {
		Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
		// The number of days to leave blank at
		// the start of this month.
		int trailingSpaces = 0;
		int leadSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;

		int currentMonth = mm - 1;
		String currentMonthName = getMonthAsString(currentMonth);
		daysInMonth = getNumberOfDaysOfMonth(currentMonth, yy);

		Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
				+ daysInMonth + " days.");

		// Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
		GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
		Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

		if (currentMonth == 11) {
			prevMonth = currentMonth - 1;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth, yy);
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy + 1;

		} else if (currentMonth == 0) {
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth, yy);
			nextMonth = 1;

		} else {
			prevMonth = currentMonth - 1;
			nextMonth = currentMonth + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth, yy);

		}

		// Compute how much to leave before before the first day of the
		// month.
		// getDay() returns 0 for Sunday.
		int currentWeekDay = getDay(cal);
		trailingSpaces = currentWeekDay;

		if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
			++daysInMonth;
		}

		// Trailing Month days
		for (int i = 0; i < trailingSpaces; i++) {
			Log.d(tag,
					"PREV MONTH:= "
							+ prevMonth
							+ " => "
							+ getMonthAsString(prevMonth)
							+ " "
							+ String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
									+ i));
			list.add(String
					.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
							+ i)
					+ "-GREY"
					+ "-"
					+ getMonthAsString(prevMonth)
					+ "-"
					+ prevYear);
		}

		// Current Month Days
		for (int i = 1; i <= daysInMonth; i++) {
			Log.d(currentMonthName, String.valueOf(i) + " "
					+ getMonthAsString(currentMonth) + " " + yy);
			if (i == getCurrentDayOfMonth()) {
				list.add(String.valueOf(i) + "-BLUE" + "-"
						+ getMonthAsString(currentMonth) + "-" + yy);
			} else {
				list.add(String.valueOf(i) + "-WHITE" + "-"
						+ getMonthAsString(currentMonth) + "-" + yy);
			}
		}

		// Leading Month days
		for (int i = 0; i < list.size() % 7; i++) {
			Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
			list.add(String.valueOf(i + 1) + "-GREY" + "-"
					+ getMonthAsString(nextMonth) + "-" + nextYear);
		}
	}

	private int getDay(Calendar cal) {
		int retVal = cal.get(Calendar.DAY_OF_WEEK) - 2;
		retVal = retVal < 0 ? 6 : retVal;
		return retVal;
	}

	/**
	 * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve ALL
	 * entries from a SQLite database for that month. Iterate over the List of
	 * All entries, and get the dateCreated, which is converted into day.
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private HashMap findNumberOfEventsPerMonth(int year, int month) {
		HashMap map = new HashMap<String, Integer>();
		// DateFormat dateFormatter2 = new DateFormat();
		//
		// String day = dateFormatter2.format("dd", dateCreated).toString();
		//
		// if (map.containsKey(day))
		// {
		// Integer val = (Integer) map.get(day) + 1;
		// map.put(day, val);
		// }
		// else
		// {
		// map.put(day, 1);
		// }
		return map;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		LayoutInflater inflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(R.layout.day_gridcell, parent, false);

		// Get a reference to the Day gridcell
		gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
		gridcell.setOnClickListener(this);

		// ACCOUNT FOR SPACING
		mPille = (ImageView) row.findViewById(R.id.pille_icon);
		mPilleStart = (ImageView) row.findViewById(R.id.pille_star_icon);
		mPlus = (ImageView) row.findViewById(R.id.plus_icon);
		mIntim = (ImageView) row.findViewById(R.id.intim_icon);
		mNotes = (ImageView) row.findViewById(R.id.notes_icon);
		mPeriode = (ImageView) row.findViewById(R.id.periode_icon);

		Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
		String[] day_color = list.get(position).split("-");
		String theday = day_color[0];
		String themonth = day_color[2].substring(0, 3);
		String theyear = day_color[3];
		if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
			if (eventsPerMonthMap.containsKey(theday)) {
				num_events_per_day = (TextView) row
						.findViewById(R.id.num_events_per_day);
				Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
				num_events_per_day.setText(numEvents.toString());
			}
		}

		// Set the Day GridCell
		gridcell.setText(theday);
		gridcell.setTag(theday + "-" + themonth + "-" + theyear);
		Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
				+ theyear);

		if (day_color[1].equals("GREY")) {
			gridcell.setTextColor(Color.LTGRAY);
		}
		if (day_color[1].equals("WHITE")) {
			gridcell.setTextColor(Color.WHITE);
		}
		if (day_color[1].equals("BLUE")) {
			gridcell.setTextColor(Color.parseColor("#003366"));
		}
		SharedPreferences sharedPreferences = _context.getSharedPreferences(
				BaseActivity.REMEMBERME, BaseActivity.MODE_WORLD_READABLE);
		if (sharedPreferences.contains(BaseActivity.FIRST_DAY)) {

			if ((getDayPosition(theday + "-" + themonth + "-" + theyear) < BaseActivity.firstDay - 6 && getDayPosition(theday
					+ "-" + themonth + "-" + theyear) > BaseActivity.theFirstDate)
					|| (getDayPosition(theday + "-" + themonth + "-" + theyear) > BaseActivity.firstDay && getDayPosition(theday
							+ "-" + themonth + "-" + theyear) < BaseActivity.firstDay + 22)
					&& getDayPosition(theday + "-" + themonth + "-" + theyear) > BaseActivity.theFirstDate
					|| getDayPosition(theday + "-" + themonth + "-" + theyear) > BaseActivity.firstDay + 28) {
				mPille.setVisibility(ImageView.VISIBLE);
				counter++;
			} else {
				mPille.setVisibility(ImageView.INVISIBLE);
			}

		}
		mDayNoteDataSource.open();
		DayNote dayNote = mDayNoteDataSource.getDayNoteByDate(theday + "-"
				+ themonth + "-" + theyear);
		mDayNoteDataSource.close();
		if (dayNote != null) {

			if (dayNote.getNote() != null && !dayNote.getNote().equals("")) {
				mNotes.setVisibility(ImageView.VISIBLE);
			}
			if (dayNote.getIsIntim() != null
					&& !dayNote.getIsIntim().equals("false")) {
				mIntim.setVisibility(ImageView.VISIBLE);
			}
			if (dayNote.getMenstruation() != null
					&& !dayNote.getMenstruation().equals("")) {
				mPeriode.setVisibility(ImageView.VISIBLE);
			}
			if (dayNote.getArzttermin() != null
					&& !dayNote.getArzttermin().equals("")) {
				mPlus.setVisibility(ImageView.VISIBLE);
			}

			if ((dayNote.getSymptoms() != null
					&& !dayNote.getSymptoms().equals(""))
                || (dayNote.getStimmungs()!=null && !dayNote.getStimmungs().equals(""))) {
				mNotes.setVisibility(ImageView.VISIBLE);
			}
			if (dayNote.getBegin_or_end_pille_date() != null
					&& !dayNote.getBegin_or_end_pille_date().equals("")) {
				mPilleStart.setVisibility(ImageView.VISIBLE);
			}
		}
		row.setSelected(false);
		return row;
	}

	public void onClick(View view) {

		if (lastSelectedCell == null) {
			lastSelectedCell = view;
		} else {
			lastSelectedCell.setSelected(false);
			lastSelectedCell = view;
		}

		String date_month_year = (String) view.getTag();
		// view.setPressed(true);
		view.setSelected(true);
		mDayNoteDataSource.open();
		DayNote dayNote = mDayNoteDataSource.getDayNoteByDate(date_month_year);
		MainCalendarActivity.day_month_year = date_month_year;
		mDayNoteDataSource.close();
		mAction.setSelectedDayNote(dayNote);
		MainCalendarActivity.enable();

	}

	public int getCurrentDayOfMonth() {
		return currentDayOfMonth;
	}

	private void setCurrentDayOfMonth(int currentDayOfMonth) {
		this.currentDayOfMonth = currentDayOfMonth;
	}

	public void setCurrentWeekDay(int currentWeekDay) {
		this.currentWeekDay = currentWeekDay;
	}

	public int getCurrentWeekDay() {
		return currentWeekDay;
	}

	private int getDayPosition(String date) {

		String[] dateInfo = date.split("-");
		int monthCount = 0;
		int daysWhenYearStarted = 0;
		for (int i = 0; i < 12; i++) {
			if (months[i].contains(dateInfo[1])) {
				monthCount = i;
				break;
			}
		}

		for (int i = 0; i < monthCount; i++) {
			daysWhenYearStarted += daysOfMonth[i];
		}
		daysWhenYearStarted += Integer.parseInt(dateInfo[0]);
		return daysWhenYearStarted;
	}

}