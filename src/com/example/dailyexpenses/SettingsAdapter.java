package com.example.dailyexpenses;

import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SettingsAdapter extends ArrayAdapter<String>{

	private Context context;
	private int resourceId;
	private List<String> settingsList;
	private TextView itemTextView;
	
	public SettingsAdapter(Context context, int resourceId, List<String> settingsList) {
		super(context, resourceId, settingsList);
		this.context = context;
		this.resourceId = resourceId;
		this.settingsList = settingsList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = convertView;
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/LANENAR.ttf");
		
		if(view == null){
			if(position == 0 || position == 2){
				view = inflater.inflate(R.layout.settings_header_item,parent, false);
				TextView headerTextView = (TextView) view.findViewById(R.id.settingsHeaderTextView);
				headerTextView.setTypeface(tf);
				
				headerTextView.setText(settingsList.get(position));
			}else{
				view = inflater.inflate(resourceId,parent, false);
				itemTextView = (TextView) view.findViewById(R.id.settingsItemTextView);
				itemTextView.setText(settingsList.get(position));
				itemTextView.setTypeface(tf);
			}
			
		}
		return view;
	}
}
