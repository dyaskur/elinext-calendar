package com.rememberme.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayNote {
	private static final String DATE_FORMAT = "dd-mmm-yyyy";
	private Long id;
	private String note;
	private String menstruation;
	private String stimmungs;
	private String symptoms;
	private String date;
	private String begin_or_end_pille_date;
	private String arzttermin;
	private int month;
	private String isIntim;

	public String getIsIntim() {
		return isIntim;
	}

	public void setIsIntim(String isIntim) {
		this.isIntim = isIntim;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMenstruation() {
		return menstruation;
	}

	public void setMenstruation(String menstruation) {
		this.menstruation = menstruation;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getStimmungs() {
		return stimmungs;
	}

	public void setStimmungs(String stimmungs) {
		this.stimmungs = stimmungs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setListStimmungs(List<String> list) {
		stimmungs = "";

		for (String i : list) {
			stimmungs += i + "/";
		}
	}

	public void setListSymptoms(List<String> list) {
		symptoms = "";

		for (String i : list) {
			symptoms += i + "/";
		}
	}

	public List<String> getNormalizedStimmungs() {
		ArrayList<String> stimmungsList = new ArrayList<String>();
		if(stimmungs == null) {
			return stimmungsList;
		}
		
		String[] array = stimmungs.split("/");

		for (String i : array) {
			stimmungsList.add(i);
		}

		return stimmungsList;
	}

	public List<String> getNormalizedSymptoms() {
		ArrayList<String> symptomsList = new ArrayList<String>();
		if(symptoms == null) {
			return symptomsList;
		}
		
		String[] array = symptoms.split("/");

		for (String i : array) {
			symptomsList.add(i);
		}

		return symptomsList;

	}

	public Date getStringDateAsDateObj() {

		return null;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	

	public static String converDateToString(Date dateConvert) {
		if(dateConvert == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(dateConvert);

	}

	public static Date convertStringDateToDateObj(String setDate) {
		if (setDate != null && !setDate.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			try {
				Date dateObj = (Date) formatter.parse(setDate);
				return dateObj;

			} catch (ParseException e) {
				e.printStackTrace();
				return null;

			}
		}

		return null;

	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getBegin_or_end_pille_date() {
		return begin_or_end_pille_date;
	}

	public void setBegin_or_end_pille_date(String begin_or_end_pille_date) {
		this.begin_or_end_pille_date = begin_or_end_pille_date;
	}

	public String getArzttermin() {
		return arzttermin;
	}

	public void setArzttermin(String arzttermin) {
		this.arzttermin = arzttermin;
	}



}
