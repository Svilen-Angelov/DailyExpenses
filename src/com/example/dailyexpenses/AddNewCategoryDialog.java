package com.example.dailyexpenses;

import java.util.List;

import database.DBHelper;
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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewCategoryDialog extends DialogFragment{

	private EditText categoryName;
	private DBHelper db;
	private View view;
	
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		
		view = inflater.inflate(R.layout.add_new_category_dialog, null);
		categoryName = (EditText) view.findViewById(R.id.newCategoryDialogEditText);
		
		builder.setView(view)
		.setPositiveButton(R.string.addNewCategorySave, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				addCategory();
				Toast.makeText(getActivity(), "Added",Toast.LENGTH_LONG).show();
			}
		})
		.setNegativeButton(R.string.addNewCategoryCancel, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		return builder.create();
	}
	
	private void addCategory(){
		
		db = new DBHelper(getActivity());
		
		Category category = new Category(categoryName.getText().toString());
		
		db.addCategory(category);
		//****
		DBHelper helper = new DBHelper(getActivity());
		List<Category> c = helper.getAllCategoies(false);
		for(Category cat: c){
			Log.i("pes", "ID in obj: " + cat.getCategory_id());
		}
			//***
	}
}
