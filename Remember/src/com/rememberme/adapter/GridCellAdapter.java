package com.rememberme.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rememberme.R;
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

	// Days in Current Month
	public GridCellAdapter(Context context, int textViewResourceId, int month,
			int year, DayNoteLoadAction action, DayNoteDataSource dataSource) {
		super();
		this._context = context;
		this.list = new ArrayList<String>();
		this.month = month;
		this.year = year;
		mAction = action;
		mDayNoteDataSource = dataSource;

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
			Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:" + prevMonth
					+ " NextMonth: " + nextMonth + " NextYear: " + nextYear);
		} else if (currentMonth == 0) {
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth, yy);
			nextMonth = 1;
			Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
					+ prevMonth + " NextMonth: " + nextMonth + " NextYear: "
					+ nextYear);
		} else {
			prevMonth = currentMonth - 1;
			nextMonth = currentMonth + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth, yy);
			Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
					+ prevMonth + " NextMonth: " + nextMonth + " NextYear: "
					+ nextYear);
		}

		// Compute how much to leave before before the first day of the
		// month.
		// getDay() returns 0 for Sunday.
		int currentWeekDay = getDay(cal);
		trailingSpaces = currentWeekDay;

		Log.d(tag, "Week Day:" + currentWeekDay + " is "
				+ getWeekDayAsString(currentWeekDay));
		Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
		Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

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
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) _context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.day_gridcell, parent, false);
		}

		// Get a reference to the Day gridcell
		gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
		gridcell.setOnClickListener(this);

		// ACCOUNT FOR SPACING

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

		setMarks(row, theday, themonth, theyear);

		return row;
	}

	private void setMarks(View row, String day, String month, String year) {
		

		DayNote dayNote = mDayNoteDataSource.getDayNoteByDate(day + '-' + month
				+ '-' + year);

		if (dayNote != null) {
			setIntim(row, dayNote);
			setEintrag(row, dayNote);
			setArzttermin(row, dayNote);
			setPeriod(row, dayNote);
		} else {
			hideUnused(row);
		}

		
	}

	private void setPeriod(View row, DayNote dayNote) {
		String mestruation = dayNote.getMenstruation();
		
		if (mestruation == null || mestruation.equals("")){
			row.findViewById(R.id.period).setVisibility(View.GONE);
		} else {
			row.findViewById(R.id.period).setVisibility(View.VISIBLE);
		}
	}

	private void setArzttermin(View row, DayNote dayNote) {
		String arzttermin = dayNote.getArzttermin();
		if (arzttermin == null || arzttermin.equals("")){
			row.findViewById(R.id.plus_icon).setVisibility(View.GONE);
		} else {
			row.findViewById(R.id.plus_icon).setVisibility(View.VISIBLE);
		}
		
	}

	private void setEintrag(View row, DayNote dayNote) {
		String note = dayNote.getNote();
		if (note == null || note.equals("")) {
			row.findViewById(R.id.notes_icon).setVisibility(View.GONE);
		} else {
			row.findViewById(R.id.notes_icon).setVisibility(View.VISIBLE);
		}
	}

	private void hideUnused(View row) {
		row.findViewById(R.id.intim_icon).setVisibility(View.GONE);	
		row.findViewById(R.id.notes_icon).setVisibility(View.GONE);
		row.findViewById(R.id.period).setVisibility(View.GONE);
		row.findViewById(R.id.plus_icon).setVisibility(View.GONE);
	}

	private void setIntim(View row, DayNote dayNote) {
		boolean isIntim = Boolean.parseBoolean(dayNote.getIsIntim());
		if (isIntim) {
			row.findViewById(R.id.intim_icon).setVisibility(View.VISIBLE);
		} else {
			row.findViewById(R.id.intim_icon).setVisibility(View.GONE);
		}
	}

	public void onClick(View view) {

		if (lastSelectedCell == null) {
			lastSelectedCell = view;
		} else {
			lastSelectedCell.setSelected(false);
			lastSelectedCell = view;
		}

		String date_month_year = (String) view.getTag();
		view.setPressed(true);
		view.setSelected(true);
		
		DayNote dayNote = mDayNoteDataSource.getDayNoteByDate(date_month_year);
		MainCalendarActivity.day_month_year = date_month_year;
	
		mAction.setSelectedDayNote(dayNote);

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
}