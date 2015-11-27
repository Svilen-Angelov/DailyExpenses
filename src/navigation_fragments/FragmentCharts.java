package navigation_fragments;

import java.util.Arrays;
import java.util.List;

import com.example.dailyexpenses.CurrencySpinnerAdapter;
import com.example.dailyexpenses.MainActivity;
import com.example.dailyexpenses.R;
import com.example.dailyexpenses.Record;
import com.example.dailyexpenses.R.layout;

import database.DBHelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentCharts extends Fragment {

	private Spinner spinner;
	private Fragment fragmentCharts;
	private TextView infoTabTextView;
	private CurrencySpinnerAdapter chartDropDownItems;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chart_fragment, null);

		// Load PieChart Fragment by default
		//fragmentCharts = new NestedFragmentPieChart();

		String[] chartsArray = getResources().getStringArray(R.array.chart_type_items);
		List<String> listCurrencies = Arrays.asList(chartsArray);
		
		spinner = (Spinner) view.findViewById(R.id.spinner_chartTypes);
		chartDropDownItems =  new CurrencySpinnerAdapter(getActivity(), R.layout.spinner_category_simple_item, listCurrencies, getActivity());
		spinner.setAdapter(chartDropDownItems);
		
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/LANENAR.ttf");
		infoTabTextView = (TextView) view.findViewById(R.id.tabTextChart);
		infoTabTextView.setTypeface(tf);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				if (position == 0) {
					fragmentCharts = new NestedFragmentPieChart();
				} else {
					fragmentCharts = new NestedFragmentLineChart();
				}
				
				FragmentManager fm = getChildFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				transaction.replace(R.id.fragmentPlace, fragmentCharts);
				transaction.commit();
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				fragmentCharts = new NestedFragmentPieChart();
				
				FragmentManager fm = getChildFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				transaction.replace(R.id.fragmentPlace, fragmentCharts);
				transaction.commit();
			}
		});

		

		return view;
	}

}