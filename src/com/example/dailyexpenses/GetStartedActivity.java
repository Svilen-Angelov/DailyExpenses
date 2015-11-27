package com.example.dailyexpenses;

import database.DBHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class GetStartedActivity extends FragmentActivity {

	private DBHelper dbRecords;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_started);

		Fragment fr = new FragmentOverview();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.fragmentFirstPlace,fr); 
		ft.commit();
		
	}
	public DBHelper getDbRecords() {
		return dbRecords;
	}

	public void setDbRecords(DBHelper dbRecords) {
		this.dbRecords = dbRecords;
	}
}
