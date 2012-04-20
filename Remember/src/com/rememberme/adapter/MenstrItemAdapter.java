package com.rememberme.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.rememberme.R;
import com.rememberme.activity.MenstruationActivity;

public class MenstrItemAdapter extends ArrayAdapter<String> {

	private String[] mItems;
	private LayoutInflater mInflater;
	String selected;
	

	public MenstrItemAdapter(Context context, int resource,
			int textViewResourceId, String[] items,String selected) {
		super(context, resource, textViewResourceId, items);
		mItems = items;
		this.selected = selected;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup currentView = (ViewGroup) mInflater.inflate(
				R.layout.menstr_item, null);
		CheckedTextView mItem = (CheckedTextView) currentView
				.findViewById(R.id.menstr_item_name);
		mItem.setText(mItems[position]);
		
		if(MenstruationActivity.map.get(mItem.getText()) == false) {
			mItem.setChecked(false);
			mItem.setCheckMarkDrawable(R.drawable.btn_check_off);
			
		} else {
			mItem.setChecked(true);
			mItem.setCheckMarkDrawable(R.drawable.btn_check_on);
		}
		
		return currentView;
	}

}
