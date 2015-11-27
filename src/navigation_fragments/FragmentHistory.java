package navigation_fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.example.dailyexpenses.HistoryItemAdapter;
import com.example.dailyexpenses.R;
import com.example.dailyexpenses.R.layout;
import com.example.dailyexpenses.Record;

import database.DBHelper;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentHistory extends Fragment{
	
	private TextView historyInfoTab;
	private HistoryItemAdapter historyadapter;
	private DBHelper db;
	private List<Record> records;
	private ListView historyListView;
	
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.history_fragment, null);
	        
	        db = new DBHelper(getActivity());
	        records = db.getAllRecords();
	        
	        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/LANENAR.ttf");
	        historyInfoTab = (TextView) root.findViewById(R.id.tabTextHistory);
	        historyInfoTab.setTypeface(tf);
	        
	        //Change list order 
	        Collections.reverse(records);
	        
	        historyListView = (ListView) root.findViewById(R.id.historyListView);
	        
	        historyadapter = new HistoryItemAdapter(getActivity(), R.layout.history_simple_list_item, records);
	        historyListView.setAdapter(historyadapter);
	        
	        return root;
	    }
}
