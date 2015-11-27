package com.example.dailyexpenses;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CurrencySpinnerAdapter extends ArrayAdapter<String>{

	private Context context;
	private int resourceId;
	private List<String> currencyList;
	private TextView itemTextView;
	private Activity activiryForTypeFace;
	private String currency;
	
	public CurrencySpinnerAdapter(Context context, int resource,
			List<String> objects, Activity activiryForTypeFace) {
		super(context, resource, objects);
		
		this.context = context;
		this.resourceId = resource;
		this.currencyList = objects;
		this.activiryForTypeFace = activiryForTypeFace;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return initView(position, convertView, parent, resourceId, R.id.spinner_category_item);
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return initView(position, convertView, parent,R.layout.spinner_dropdown_items, R.id.spinner_category_dropdown_item);
	}
	
	private View initView(int position, View convertView, ViewGroup parent, int resource, int where) {
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = convertView;
		
		currency = currencyList.get(position);
		
		Typeface tf = Typeface.createFromAsset(activiryForTypeFace.getAssets(),"fonts/LANENAR.ttf");
		
		if(view == null){
			ViewHolder holder = new ViewHolder();
			
			view = inflater.inflate(resource, parent, false);
			
			itemTextView = (TextView) view.findViewById(where);
			
			itemTextView.setTypeface(tf);
			itemTextView.setTextColor(Color.parseColor("#FFFFFF"));
			itemTextView.setTextSize((float)20.0);
			
			holder.itemTextView = itemTextView;
			
			view.setTag(holder);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.itemTextView.setText(currency);
		
		return view;
	}
	
	private class ViewHolder{
		TextView itemTextView;
	}
}
