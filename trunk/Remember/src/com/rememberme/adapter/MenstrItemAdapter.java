package com.rememberme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.rememberme.R;

public class MenstrItemAdapter extends ArrayAdapter<String> {

    private String[] mItems;
    private LayoutInflater mInflater;

    public MenstrItemAdapter(Context context, int resource, int textViewResourceId,
                       String[] items) {
        super(context, resource, textViewResourceId, items);

        mItems = items;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewGroup currentView = (ViewGroup) mInflater.inflate(R.layout.menstr_item, null);
        CheckedTextView mItem = (CheckedTextView) currentView.findViewById(R.id.menstr_item_name);
        mItem.setText(mItems[position]);
        return currentView;
    }

}
