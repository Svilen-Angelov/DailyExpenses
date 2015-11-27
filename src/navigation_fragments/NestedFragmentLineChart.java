package navigation_fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.OnChartGestureListener;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;

import com.example.dailyexpenses.Category;
import com.example.dailyexpenses.R;
import com.example.dailyexpenses.Record;

import database.DBHelper;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NestedFragmentLineChart extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener{

	private LineChart lineChart;
	private DBHelper db;
	private List<Record> listOfRecords;
	private double balance = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.line_chart, null);
		
		db = new DBHelper(getActivity());
		listOfRecords = db.getAllRecords();
		
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/LANENAR.ttf");
		
		SharedPreferences sp = getActivity().getSharedPreferences("GlobalBalance", 0);
		balance = Double.longBitsToDouble(sp.getLong("globalbalanceInit", 0));
	
		lineChart = (LineChart) view.findViewById(R.id.lineChart);
		
		lineChart.setOnChartGestureListener(this);
		lineChart.setOnChartValueSelectedListener(this);
		
		SharedPreferences prefs = getActivity().getSharedPreferences("Currency", 0);
		lineChart.setUnit(prefs.getString("currency", "lv"));
		
		lineChart.setDrawUnitsInChart(true);
		lineChart.setDrawBorder(true);
		lineChart.setBorderPositions(new BorderPosition[] {BorderPosition.BOTTOM});
		lineChart.setTouchEnabled(true);
		lineChart.setPinchZoom(true);
		lineChart.setDescription("Balance Tracker");
		lineChart.setHighlightEnabled(true);
		lineChart.setStartAtZero(false);
		lineChart.setDrawGridBackground(false);
		lineChart.setDoubleTapToZoomEnabled(true);
		lineChart.setDescriptionTextSize((float)6.0);
		lineChart.setDrawLegend(false);
		lineChart.setNoDataText("There is no data to be showed.");
		lineChart.setValueTypeface(tf);
		lineChart.setValueTextColor(Color.parseColor("#FFFFFF"));
		lineChart.setValueTextSize((float)7.0);
		
		
		//setData(getMinRecordAmount(), getMaxRecordAmount(), listOfRecords.size());
		setData();
		
		lineChart.animateX(1000);
		return view;
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartLongPressed(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartDoubleTapped(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartSingleTapped(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX,
			float velocityY) {
	}
	
	private void setData(){
		
		ArrayList<String> xVals = new ArrayList<String>();
		
		for(double i = 0; i < listOfRecords.size() + 1; ++i){
			xVals.add(i + "");
		}
		
		ArrayList<Entry> yVals = new ArrayList<Entry>();
		
		Entry defaultEntry = new Entry((float)balance, 0);
    	
    	yVals.add(defaultEntry);
		
		int counter = 1;
		for(Record record: listOfRecords){
        	
        	float amountRaw = (float) record.getAmount();
        	
        	if(record.isIncome()){
        		balance += amountRaw;
        	}else{
        		balance -= amountRaw;
        	}
        	
        	Entry newEntry = new Entry((float)balance, counter);
        	
        	yVals.add(newEntry);
        	++counter;
        }
		
		LineDataSet lineSet = new LineDataSet(yVals, "");
		lineSet.setCircleColor(Color.parseColor("#00D5ED"));
		lineSet.setCircleSize(4f);
		lineSet.setFillAlpha(60);
		lineSet.setFillColor(Color.parseColor("#00D5ED"));
		lineSet.setLineWidth(1f);
		
		
		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		
		dataSets.add(lineSet);
		
		LineData lineData= new LineData(xVals, dataSets);
		lineChart.setData(lineData);
	}
	
}
