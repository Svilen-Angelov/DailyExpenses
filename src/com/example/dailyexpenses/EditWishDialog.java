package com.example.dailyexpenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;
import database.DBHelper;

public class EditWishDialog extends DialogFragment {

	private EditText wishName;
	private EditText wishAmount;
	private RatingBar wishRatingBar;
	private DBHelper db;
	private View view;
	private float ratingSelected;
	private Wish wish;
	
	public EditWishDialog(Wish wish){
		
		this.wish = wish;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());

		view = inflater.inflate(R.layout.edit_wish_dialog, null);
		wishName = (EditText) view.findViewById(R.id.editWishNameEditText);
		wishAmount = (EditText) view.findViewById(R.id.editWishAmountEditText);
		wishRatingBar = (RatingBar) view.findViewById(R.id.editWishRatingBar);
		
		wishName.setText(wish.getName());
		wishAmount.setText(String.valueOf(wish.getAmount()));
		wishRatingBar.setRating((float)wish.getWishRating());

		wishRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			public void onRatingChanged(RatingBar ratingBar,
					float rating, boolean fromUser) {
				ratingSelected = rating;
				Log.i("res", "AddNewWishDialog RatingBar 44: " + ratingSelected);
			}
		});
		
		builder.setView(view)
				.setPositiveButton(R.string.addNewWishSave,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								if ((wishAmount.getText().toString().length() > 0) &&
										(!wishName.getText().equals(""))) {
									updateWish();
									
									Toast.makeText(getActivity(),"Wish Updated",Toast.LENGTH_LONG).show();
								} else {
									Toast.makeText(getActivity(),"Invalid input!",Toast.LENGTH_LONG).show();
								}
							}
						})
				.setNegativeButton(R.string.addNewWishCancel,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});

		return builder.create();
	}

	private void updateWish() {

		db = new DBHelper(getActivity());

		wish.setAmount(Double.parseDouble(wishAmount.getText().toString()));
		wish.setName(wishName.getText().toString());
		wish.setWishRating((double) ratingSelected);
				
		
		Log.i("res", "AddNewWishDialog AddWishToDB: " + wish.toString());
		db.updateWish(wish);
	}

}
