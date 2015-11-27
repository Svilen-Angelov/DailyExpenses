package com.example.dailyexpenses;

import database.DBHelper;

import navigation_fragments.FragmentAddNew;
import navigation_fragments.FragmentCharts;
import navigation_fragments.FragmentHistory;
import navigation_fragments.FragmentSettings;
import navigation_fragments.FragmentWishlist;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private DrawerLayout drawerLayout;
	private ListView drawerlistView;
	private DrawerItemAdapter adapter;
	private DBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		
		db = new DBHelper(this);
		firstInstalling();
	
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragment_place, new FragmentAddNew());
		ft.commit();

		final ObjectDrawerItem[] objDrawerItems = new ObjectDrawerItem[] {
				new ObjectDrawerItem(R.drawable.addnew_icon, "Balance"),
				new ObjectDrawerItem(R.drawable.addnew_icon, "Add new"),
				new ObjectDrawerItem(R.drawable.wishlist_icon, "Wishlist"),
				new ObjectDrawerItem(R.drawable.chart_icon, "Charts"),
				new ObjectDrawerItem(R.drawable.history_icon, "History"),
				new ObjectDrawerItem(R.drawable.settings_icon, "Settings"), };
		
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerlistView = (ListView) findViewById(R.id.left_drawer);

		adapter = new DrawerItemAdapter(this, R.layout.drawer_single_row_item,
				objDrawerItems, this);
		drawerlistView.setAdapter(adapter);

		drawerlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {

				Fragment fragment = null;

				switch (position) {
				case 0:
					fragment = new FragmentAddNew();
					break;
				case 1:
					fragment = new FragmentAddNew();
					break;
				case 2:
					fragment = new FragmentWishlist();
					break;
				case 3:
					fragment = new FragmentCharts();
					break;
				case 4:
					fragment = new FragmentHistory();
					break;
				case 5:
					fragment = new FragmentSettings();
					break;
				}

				// Load a fragment
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.fragment_place, fragment);
				ft.commit();

				// Hide navigation drawer when item is pressed
				drawerLayout.closeDrawers();
			}
		});

	}

	private void firstInstalling() {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		boolean previouslyStarted = prefs
				.getBoolean("previouslyStarted", false);

		if (!previouslyStarted) {
			SharedPreferences.Editor edit = prefs.edit();
			edit.putBoolean("previouslyStarted", Boolean.TRUE);
			edit.commit();
			
			initCategories();
			
			runGetStartedActivity();
			
			
		}
	}

	private void runGetStartedActivity() {
		Intent intent = new Intent(MainActivity.this, GetStartedActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void notifyNavigationChanged() {
		adapter.notifyDataSetChanged();
	}

	private void initCategories() {

		Category foodCat = new Category("Food");
		Category drinksCat = new Category("Drinks");
		Category giftsCat = new Category("Gifts");
		Category cinemaCat = new Category("Cinema");
		Category petsCat = new Category("Pets");
		Category phoneCat = new Category("Mobile phone");
		Category carCat = new Category("Car");
		Category fuelCat = new Category("Fuel");
		Category clothesCat = new Category("Clothes");
		
		Record food = new Record(50, 1231564, false, "Shopping", 1);
		Record fish = new Record(20, 1231564, false, "Fish", 1);
		Record bread = new Record(2, 1231564, false, "Bread", 1);
		Record somethingToDrink = new Record(10, 1231564, false, "Juce", 2);
		Record milk = new Record(4, 1231564, false, "Milk", 2);
		Record expensiveDrink = new Record(35, 1231564, false, "Verry Expensive Drink", 2);
		Record moreJuce = new Record(10, 1231564, false, "More Juce", 2);
		Record gifts = new Record(45, 1231564, false, "Crismas gifts", 3);
		Record cinema = new Record(28, 1231564, false, "Movie in cinema", 4);
		Record foodForMyDog = new Record(33, 1231564, false, "Dog food for whole week", 5);
		
		db.addCategory(foodCat);
		db.addCategory(drinksCat);
		db.addCategory(giftsCat);
		db.addCategory(cinemaCat);
		db.addCategory(petsCat);
		db.addCategory(phoneCat);
		db.addCategory(carCat);
		db.addCategory(fuelCat);
		db.addCategory(clothesCat);
		
		db.addRecord(food);
		db.addRecord(fish);
		db.addRecord(bread);
		db.addRecord(somethingToDrink);
		db.addRecord(milk);
		db.addRecord(expensiveDrink);
		db.addRecord(moreJuce);
		db.addRecord(gifts);
		db.addRecord(cinema);
		db.addRecord(foodForMyDog);
		
		db.close();
	}

}
