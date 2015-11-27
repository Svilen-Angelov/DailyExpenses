package navigation_fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.dailyexpenses.Category;
import com.example.dailyexpenses.R;
import com.example.dailyexpenses.Record;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

import database.DBHelper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NestedFragmentPieChart extends Fragment{

	private PieChart pieChart;
	private DBHelper db;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.pie_chart, null);
		
		db = new DBHelper(getActivity());
		
		pieChart = (PieChart) view.findViewById(R.id.pieChart);
		
		pieChart.setDescription("Spent Monies by Category");
		//pieChart.setUsePercentValues(true);
		pieChart.setDrawHoleEnabled(false);
		pieChart.animateXY(1500, 1500);
		pieChart.setData(generatePieData());
		pieChart.setDrawYValues(true);
		pieChart.setNoDataText("There is no data to be showed.");
		
		return view;
	}
	
	protected PieData generatePieData() {
        
		List<Category> listOfCategoriesRaw = db.getAllCategoies(false);
		
		List<Category> listOfCategories =new ArrayList<Category>();
		
		for(Category c: listOfCategoriesRaw){
			if(c.getAmount() > 0){
				listOfCategories.add(c);
			}
		}
		
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        
        int counter = 0;
        for(Category category: listOfCategories){
        	
        	String name = category.getName();
        	float amount = (float) category.getAmount();
        	
        	Entry newEntry = new Entry(amount, counter);
        	
        	entries.add(newEntry);
        	xVals.add(name);
        	++counter;
        }
        int[] colors = {
            Color.rgb(224, 0, 0), 
            Color.rgb(255, 102, 0), 
            Color.rgb(231, 188, 0),
            Color.rgb(106, 150, 31), 
            Color.rgb(179, 100, 53),
            Color.rgb(154, 67, 187),
            Color.rgb(58, 172, 138),
            Color.rgb(56, 121, 181),
            Color.rgb(194, 35, 144)};
        
        PieDataSet ds1 = new PieDataSet(entries, "");
        ds1.setColors(colors);
        ds1.setSliceSpace(2f);
        
        PieData d = new PieData(xVals, ds1);
        return d;
    }
	
}
