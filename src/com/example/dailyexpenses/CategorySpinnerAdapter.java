package com.example.dailyexpenses;

import java.util.Arrays;
import java.util.List;

import database.DBHelper;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CategorySpinnerAdapter extends ArrayAdapter<Category>{

	private Context context;
	private int resourceId;
	private List<Category> categoriesList;
	private TextView itemTextView;
	private Category category;
	private Activity activiryForTypeFace;
	
	public CategorySpinnerAdapter(Context context, int resource,
			List<Category> objects, Activity activiryForTypeFace) {
		super(context, resource, objects);
		
		this.context = context;
		this.resourceId = resource;
		this.categoriesList = objects;
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
		category = categoriesList.get(position);
		
		Typeface tf = Typeface.createFromAsset(activiryForTypeFace.getAssets(),"fonts/LANENAR.ttf");
		
		if(view == null){
			ViewHolder holder = new ViewHolder();
			
			view = inflater.inflate(resource, parent, false);
			
			itemTextView = (TextView) view.findViewById(where);
			itemTextView.setTypeface(tf);
			itemTextView.setTextColor(Color.parseColor("#FFFFFF"));
			itemTextView.setTextSize((float)22.0);
			
			holder.itemTextView = itemTextView;
			
			view.setTag(holder);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.itemTextView.setText(category.getName());
		
		return view;
	}
	
	private class ViewHolder{
		TextView itemTextView;
	}
}
