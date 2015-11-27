package navigation_fragments;

import java.util.Arrays;
import java.util.List;

import com.example.dailyexpenses.AddNewCategoryDialog;
import com.example.dailyexpenses.R;
import com.example.dailyexpenses.R.layout;
import com.example.dailyexpenses.SettingsAdapter;

import database.DBHelper;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentSettings extends Fragment{
	
	private ListView settingsListView;
	private DBHelper db;
	private TextView settingsTabInfo;
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.settings_fragment, null);
	        
	        List<String> settingsList = Arrays.asList(getActivity().getResources().getStringArray(R.array.settings_items));
	        
	        db = new DBHelper(getActivity());
	        
	        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/LANENAR.ttf");
	        settingsTabInfo = (TextView) root.findViewById(R.id.tabTextAddNew);
	        settingsTabInfo.setTypeface(tf);
	        
	        SettingsAdapter settingsAdapter = new SettingsAdapter(getActivity(), R.layout.settings_listview_item, settingsList);
	        settingsListView = (ListView) root.findViewById(R.id.settingsListView);
	        settingsListView.setAdapter(settingsAdapter);
	        
	        settingsListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if(position == 1){
						DialogFragment addCategoryDialog = new AddNewCategoryDialog();
						addCategoryDialog.show(getActivity().getSupportFragmentManager(), "addcategory");
					}else if(position == 3){
						db.deleteHistory();
						Toast.makeText(getActivity(), "History Deleted",Toast.LENGTH_LONG).show();
					}
				}
			});
	        return root;
	    }
	 
	
}
