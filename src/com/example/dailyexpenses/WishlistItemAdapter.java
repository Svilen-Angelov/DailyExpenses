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
import android.widget.RatingBar;
import android.widget.TextView;
import database.DBHelper;

public class WishlistItemAdapter extends ArrayAdapter<Wish>{
	
	private Context context;
	private int layoutResourceId;
	private List<Wish> wishList;
	TextView nameTextView;
	TextView amountTextView;
	RatingBar wishRating;
	private Activity activity;
	
	public WishlistItemAdapter(Context context, int layoutResourceId, List<Wish> wishList, Activity activity) {
		super(context, layoutResourceId, wishList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.wishList = wishList;
		this.activity = activity;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = convertView;
		
		Wish wish = wishList.get(position);
		
		Typeface tf = Typeface.createFromAsset(activity.getAssets(),"fonts/LANENAR.ttf");
		
		if(view == null){
			
			ViewHolder holder = new ViewHolder();
			
			view = inflater.inflate(layoutResourceId, parent, false);
			
			nameTextView = (TextView) view.findViewById(R.id.wishlist_name);
			amountTextView = (TextView) view.findViewById(R.id.wishlist_amount);
			wishRating = (RatingBar) view.findViewById(R.id.wishlist_rating);
			
			nameTextView.setTypeface(tf);
			nameTextView.setTextSize((float) 20.0);
			nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
			
			amountTextView.setTypeface(tf);
			amountTextView.setTextSize((float) 20.0);
			amountTextView.setTextColor(Color.parseColor("#FFFFFF"));
			
			amountTextView.setText(String.valueOf(wish.getAmount()));
			nameTextView.setText(wish.getName());
			wishRating.setRating((float)wish.getWishRating());
			
			holder.rating = wishRating;
			holder.name = nameTextView;
			holder.amount = amountTextView;
			
			
			view.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		
		holder.amount.setText(String.valueOf(wish.getAmount()));
		holder.name.setText(wish.getName());
		holder.rating.setRating((float)wish.getWishRating());
		
		
		amountTextView.setText(String.valueOf(wish.getAmount()));
		nameTextView.setText(wish.getName());
		wishRating.setRating((float)wish.getWishRating());
		
		return view;
	}
	
	private class ViewHolder{
		
		TextView name;
		TextView amount;
		RatingBar rating;

	}

}
