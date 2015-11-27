package com.example.dailyexpenses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DBHelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HistoryItemAdapter extends ArrayAdapter<Record>{

	private Context context;
	private int layoutResourceId;
	private List<Record> recordList = null;
	private DBHelper db;
	TextView categoryTextView;
	TextView dateTextView;
	TextView amountTextView;
	
	public HistoryItemAdapter(Context context, int layoutResourceId, List<Record> recordList) {
		super(context, layoutResourceId, recordList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.recordList = recordList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = convertView;
		
		db = new DBHelper(getContext());
		
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/LANENAR.ttf");
		
		if(view == null){
			
			ViewHolder holder = new ViewHolder();
			
			view = inflater.inflate(layoutResourceId, parent, false);	
			categoryTextView = (TextView) view.findViewById(R.id.text_category);
			dateTextView = (TextView) view.findViewById(R.id.text_date);
			amountTextView = (TextView) view.findViewById(R.id.text_amount);
			
			categoryTextView.setTypeface(tf);
			categoryTextView.setTextSize((float) 26.0);
			
			dateTextView.setTypeface(tf);
			dateTextView.setTextSize((float) 19.0);
			
			amountTextView.setTypeface(tf);
			amountTextView.setTextSize((float) 26.0);
			
			
			holder.category = categoryTextView;
			holder.date = dateTextView;
			holder.amount = amountTextView;
			
			view.setTag(holder);
		}
		
		Log.i("res", "position: " + position);
		Log.i("res", "recordList.size(): " + recordList.size());
		
		Record record = recordList.get(position);
		
		long l = record.getDate();
		String dateString = DateFormat.format("dd-MM-yyyy", new Date(l)).toString();
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		holder.category.setText((db.getCategoryByID(record.getCategoryID())).getName());
		holder.date.setText(dateString);
		holder.amount.setText(String.valueOf(record.getAmount()));
		
		if(record.isIncome()){
			amountTextView.setTextColor(Color.parseColor("#90ed7b"));
		}else{
			amountTextView.setTextColor(Color.parseColor("#C91818"));
		}
		
		return view;
	}
	
	private class ViewHolder{
		
		TextView category;
		TextView date;
		TextView amount;
	}
}
