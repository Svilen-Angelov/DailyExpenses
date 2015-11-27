package com.example.dailyexpenses;

import database.DBHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentInit extends Fragment {

	
	private TextView salaryInfoText;
	private TextView currencyInfoText;
	private Button button;
	private EditText balanceEditText;
	private DBHelper db;
	private Spinner spinner;
	private String selectedCurrency = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragmentinit, null);
		fidnViewById(view);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quicksand-Light.otf");
		
		salaryInfoText.setTypeface(tf);
		currencyInfoText.setTypeface(tf);
		db = new DBHelper(getActivity());
		
		
		
		SharedPreferences currency = getActivity().getApplicationContext().getSharedPreferences("Currency",0);
		final Editor editorCurrency = currency.edit();
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				
				switch (spinner.getItemAtPosition(position).toString()) {
				case "BGN": selectedCurrency = "BGN";
					break;
				case "US Dollar":selectedCurrency = "$";
					break;
				case "Euro":selectedCurrency = "ˆ";
					break;

				}
				editorCurrency.putString("currency", selectedCurrency);
				editorCurrency.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				editorCurrency.putString("currency", spinner.getItemAtPosition(0).toString());
			}
		});
		
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("GlobalBalance",0);
				Editor editor = sp.edit();
				
				if(balanceEditText.getText().toString() != "" || (selectedCurrency.length() > 0)){
					
				String s = balanceEditText.getText().toString();
				long balance = Double.doubleToRawLongBits(Double.parseDouble(s));
				editor.putLong("globalbalance", balance);
				editor.putLong("globalbalanceInit", balance);
				editor.commit();
				
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
				
				}else{
					Toast.makeText(getActivity(), R.string.invalidInputGetStartedInit, Toast.LENGTH_LONG).show();
				}
			}
		});
		return view;
	}

	private void fidnViewById(View view){
		
		currencyInfoText = (TextView) view.findViewById(R.id.currencyInfoTextView);
		salaryInfoText = (TextView) view.findViewById(R.id.salaryInfoTextView);
		button = (Button) view.findViewById(R.id.next);
		balanceEditText = (EditText) view.findViewById(R.id.inputBalance);
		spinner = (Spinner) view.findViewById(R.id.spinner_currencies);
	}
}
