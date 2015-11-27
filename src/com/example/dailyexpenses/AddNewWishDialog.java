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

public class AddNewWishDialog extends DialogFragment {

	private EditText wishName;
	private EditText wishAmount;
	private RatingBar wishRatingBar;
	private DBHelper db;
	private View view;
	private float ratingSelected;

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());

		view = inflater.inflate(R.layout.add_new_wish_dialog, null);
		wishName = (EditText) view.findViewById(R.id.newWishNameEditText);
		wishAmount = (EditText) view.findViewById(R.id.newWishAmountEditText);
		wishRatingBar = (RatingBar) view.findViewById(R.id.newWishRatingBar);

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
									addWish();
									
									Toast.makeText(getActivity(),"Added",Toast.LENGTH_LONG).show();
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

	private void addWish() {

		db = new DBHelper(getActivity());

		Wish wish = new Wish(
				Double.parseDouble(wishAmount.getText().toString()), 
				wishName.getText().toString(), 
				(double) ratingSelected);
		
		Log.i("res", "AddNewWishDialog AddWishToDB: " + wish.toString());
		db.addWish(wish);
	}

}
