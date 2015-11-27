package com.example.dailyexpenses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerItemAdapter extends ArrayAdapter<ObjectDrawerItem> {

	Context context;
	int layoutResourceId;
	ObjectDrawerItem data[] = null;
	Activity a;
	private TextView balanceValue;

	DrawerItemAdapter(Context context, int layoutResourceId,
			ObjectDrawerItem[] data, Activity a) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.a = a;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		
		//
		Typeface tf = Typeface.createFromAsset(a.getAssets(),
				"fonts/LANENAR.ttf");
		
		View view = convertView;
		
		//Set Clickable/Unclickable item of the list
		isEnabled(position);
		
		if(position == 0){
			
			view = inflater.inflate(R.layout.drawer_balance_view, parent, false);
			TextView balanceText = (TextView) view.findViewById(R.id.balance_text);
			balanceValue = (TextView) view.findViewById(R.id.current_balance);
			balanceText.setTypeface(tf);
			balanceValue.setText(showBalance());
			balanceValue.setTypeface(tf);
			
			
			return view;
		}
		else{
			//Optimization
			if(view == null){
				view = inflater.inflate(layoutResourceId, parent, false);
			}
		}
		
		ImageView icon = (ImageView) view.findViewById(R.id.drawer_single_icon_image_view);
		TextView text = (TextView) view.findViewById(R.id.drawer_single_item_text_view);
		text.setTypeface(tf);

		ObjectDrawerItem objDrawerItem = data[position];
		icon.setImageResource(objDrawerItem.getIcon());
		text.setText(objDrawerItem.getName());

		return view;
	}
	
	/**
	 * If false - the list item is unclickable.
	 *  In this case the first item.
	 */
	@Override
		public boolean isEnabled(int position) {
		if(position == 0){
			return false;
		}
		return true;
		}
	
	private String showBalance(){
		
		SharedPreferences sp = getContext().getApplicationContext().getSharedPreferences("GlobalBalance", 0);
		long l = sp.getLong("globalbalance", 1);
		double d = Double.longBitsToDouble(l);
		
		String currency = getContext().getSharedPreferences("Currency", 0).getString("currency", "Лева");
		
		String formatCurrency = null;
		if(currency == "BGN"){
			formatCurrency = String.format("%.2f", d) + "lv";
		}else if(currency == "$") {
			formatCurrency = String.format("$%.2f", d);
		}else{
			formatCurrency = String.format("€%.2f", d);
		}
		if(d < 0){
			balanceValue.setTextColor(Color.parseColor("#D20A0A"));
		}else{
			balanceValue.setTextColor(Color.parseColor("#00D5ED"));
		}
		return formatCurrency;
	}
}
