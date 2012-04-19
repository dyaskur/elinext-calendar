package com.rememberme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.rememberme.R;

public class ItemAdapter extends ArrayAdapter<String> {

	private String[] mItems;
	private LayoutInflater mInflater;

    public ItemAdapter(Context context, int resource, int textViewResourceId,
                         String[] items) {
		super(context, resource, textViewResourceId, items);

		mItems = items;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewGroup currentView = (ViewGroup) mInflater.inflate(R.layout.item, null);
		currentView.setFocusable(true);
		currentView.setClickable(true);
        CheckedTextView mItem = (CheckedTextView) currentView.findViewById(R.id.item_name);
        mItem.setText(mItems[position]);
		return currentView;
	}

}
