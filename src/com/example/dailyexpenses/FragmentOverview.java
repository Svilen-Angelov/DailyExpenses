package com.example.dailyexpenses;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentOverview extends Fragment {

	private Button button;
	private TextView welcomeText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragmentoverview, null);

		button = (Button) view.findViewById(R.id.getStarted);
		welcomeText = (TextView) view.findViewById(R.id.welcomeText);

		Typeface buttonFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Light.otf");
		button.setTypeface(buttonFont);
		welcomeText.setTypeface(buttonFont);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Fragment fr = new FragmentInit();

				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragmentFirstPlace, fr);
				ft.commit();
			}
		});

		return view;
	}
}
