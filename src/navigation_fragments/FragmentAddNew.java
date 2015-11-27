package navigation_fragments;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import database.DBHelper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyexpenses.Category;
import com.example.dailyexpenses.CategorySpinnerAdapter;
import com.example.dailyexpenses.CurrencySpinnerAdapter;
import com.example.dailyexpenses.FragmentInit;
import com.example.dailyexpenses.GetStartedActivity;
import com.example.dailyexpenses.MainActivity;
import com.example.dailyexpenses.R;
import com.example.dailyexpenses.Record;

public class FragmentAddNew extends Fragment implements OnClickListener {

	private Button doneButton;
	private TextView infoBigText;
	private TextView amountTextView;
	private TextView dateTextView;
	private TextView typeTextView;
	private TextView categoryTextView;
	private TextView currencyTextView;
	private TextView descriptionTextView;
	private EditText textAmount;
	private EditText textDescription;
	private boolean isIncome;
	private long categoryID;
	private EditText dateEditText;
	private ImageView image;
	private Spinner spinner;
	private Spinner currencySpinner;
	private RadioGroup rg;
	
	private RadioButton rgIncome;
	private RadioButton rgExpense;
	
	private SimpleDateFormat dateFormatter;
	private DatePickerDialog datePickerDialog;
	private DBHelper db;
	private Animation animation;
	private long dateInMillis;
	private CategorySpinnerAdapter spinner_adapter;
	private CurrencySpinnerAdapter currenciees_spinner_adapter;
	private List<Category> listCategories;
	private String selectedCurrency;

	private static final double BGN_TO_EURO = 0.5113;
	private static final double BGN_TO_DOLLAR = 0.6285;
	private static final double EUR_TO_DOLLAR = 1.2295;
	private static final double EUR_TO_BGN = 1.9558;
	private static final double DOLLAR_TO_EUR = 0.8135;
	private static final double DOLLAR_TO_BGN = 1.591;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.add_record_fragment, null);
		
//		image = (android.widget.ImageView) root.findViewById(R.id.topImageAddNew);
//        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out_animation);
//        
//        image.startAnimation(animation);
		
		findViewsById(root);
		db = new DBHelper(getActivity());
		
		
		
		isIncome = false;
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		Calendar cal = Calendar.getInstance();
		dateEditText.setText(dateFormatter.format(cal.getTime()));
		dateInMillis = cal.getTimeInMillis();
		listCategories = db.getAllCategoies(true);
		
		spinner_adapter = new CategorySpinnerAdapter(getActivity(), R.layout.spinner_category_simple_item, listCategories, getActivity());
		
		spinner.setAdapter(spinner_adapter);
		
		String[] currenciesArray = getResources().getStringArray(R.array.currencies_items);
		List<String> listCurrencies = Arrays.asList(currenciesArray);
		
		currenciees_spinner_adapter = new CurrencySpinnerAdapter(getActivity(), R.layout.spinner_category_simple_item, listCurrencies, getActivity());
		currencySpinner.setAdapter(currenciees_spinner_adapter);
		
		categoryID = ((Category) spinner.getItemAtPosition(0)).getCategory_id();

		dateEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					setDateTimeField();
					datePickerDialog.show();
				}
			}
		});

		currencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				
				switch (currencySpinner.getItemAtPosition(position).toString()) {
			    case "BGN": selectedCurrency = "BGN";
			     break;
			    case "US Dollar": selectedCurrency  = "$";
			     break;
			    case "Euro": selectedCurrency = "&euro";
			     break;

			    }
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				selectedCurrency = "BGN";
			}
		});
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//if amount filed is empty do nothing
				if (!textAmount.getText().toString().equals("")) {

					double amount = Double.parseDouble(textAmount.getText().toString());
					amount = convertToMainCurrency(amount, selectedCurrency);
					Record newRecordtoDB = new Record(
							amount, 
							dateInMillis,
							isIncome, 
							textDescription.getText().toString(),
							categoryID);

					db.addRecord(newRecordtoDB);
					
					double getAmount = db.getCategoryByID(categoryID).getAmount() + amount;
					db.updateCategoryAmount(getAmount, categoryID);

					// Change the global balance in Navigation drawer
					if (isIncome) {
						incomeToSharedPrefs(amount);
						((MainActivity) getActivity())
								.notifyNavigationChanged();
					} else {
						expenseToSharedPrefs(amount);
						((MainActivity) getActivity())
								.notifyNavigationChanged();
					}
					
					Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
					
				} else {
					Toast.makeText(getActivity(), "Please, input amount",Toast.LENGTH_LONG).show();
				}
			}
		});
		

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.radiobutton_income:
					isIncome = true;
					break;
				case R.id.radiobutton_expenses:
					isIncome = false;
					break;
				}
			}
		});
		
		//Categories dropdown menu
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				categoryID =((Category) spinner.getItemAtPosition(position)).getCategory_id();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				categoryID = ((Category) spinner.getItemAtPosition(0)).getCategory_id();
			}
		});

		return root;
	}
	
	public void setDateTimeField() {
		dateEditText.setOnClickListener(this);

		Calendar newCalendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(getActivity(),
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Calendar newDate = Calendar.getInstance();
						newDate.set(year, monthOfYear, dayOfMonth);
						dateEditText.setText(dateFormatter.format(newDate
								.getTime()));

						dateInMillis = newDate.getTimeInMillis();
					}
				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onClick(View v) {
		if (v == dateEditText) {
			datePickerDialog.show();
		}
	}

	/**
	 * @param v
	 */
	private void findViewsById(View v) {
		
		dateEditText = (EditText) v.findViewById(R.id.dateText);
		textAmount = (EditText) v.findViewById(R.id.editTextAmount);
		textAmount.setImeOptions(EditorInfo.IME_ACTION_DONE);
		textDescription = (EditText) v.findViewById(R.id.editTextDescription);
		textDescription.setImeOptions(EditorInfo.IME_ACTION_DONE);
		doneButton = (Button) v.findViewById(R.id.button_done);
		spinner = (Spinner) v.findViewById(R.id.spinner_categories);
		currencySpinner = (Spinner) v.findViewById(R.id.currency_categories);
		rg = (RadioGroup) v.findViewById(R.id.radioGroup);
		
		rgIncome = (RadioButton) v.findViewById(R.id.radiobutton_income);
		rgExpense = (RadioButton) v.findViewById(R.id.radiobutton_expenses);
		
		infoBigText = (TextView) v.findViewById(R.id.tabTextAddNew);
		amountTextView = (TextView) v.findViewById(R.id.amountTextView);
		dateTextView = (TextView) v.findViewById(R.id.dateTextView);
		typeTextView = (TextView) v.findViewById(R.id.typeTextView);
		categoryTextView = (TextView) v.findViewById(R.id.categoryTextView);
		currencyTextView = (TextView) v.findViewById(R.id.currencyTextView);
		descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
		
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/LANENAR.ttf");
		
		rgIncome.setTypeface(tf);
		rgExpense.setTypeface(tf);
		
		infoBigText.setTypeface(tf);
		amountTextView.setTypeface(tf);
		dateTextView.setTypeface(tf);
		typeTextView.setTypeface(tf);
		categoryTextView.setTypeface(tf);
		currencyTextView.setTypeface(tf);
		descriptionTextView.setTypeface(tf);
		
		dateEditText.setTypeface(tf);
		textAmount.setTypeface(tf);
		textDescription.setTypeface(tf);


		
		
	}

		

	private void incomeToSharedPrefs(double amount){
		
		SharedPreferences sp = getActivity().getSharedPreferences("GlobalBalance", 0);
		double content = Double.longBitsToDouble(sp.getLong("globalbalance", 1));
		Editor e = sp.edit();
		e.putLong("globalbalance",Double.doubleToRawLongBits(content + amount));
		e.commit();
	}
	
	private void expenseToSharedPrefs(double amount){
		
		SharedPreferences sp = getActivity().getSharedPreferences("GlobalBalance", 0);
		double content = Double.longBitsToDouble(sp.getLong("globalbalance", 1));
		Editor e = sp.edit();
		e.putLong("globalbalance",Double.doubleToRawLongBits(content - amount));
		e.commit();
	}
	

	private double convertToMainCurrency(double amount, String currencySelected){
		String currency = getActivity().getApplicationContext().getSharedPreferences("Currency", 0).getString("currency", "BGN");

		Log.i("res", "Currency: Current = " + currencySelected + " , Main = " + currency );
		
		if(currency.equals(currencySelected)){
			return amount;
		}
		
		if(currencySelected.equals("BGN")){
			switch (currency) {
			case "$":
					amount *= BGN_TO_DOLLAR;
					break;
			case "&euro":
					amount *= BGN_TO_EURO;
					break;
			}
		}
		
		if(currencySelected.equals("&euro")){
			switch (currency) {
			case "$":
					amount *= EUR_TO_DOLLAR;
					break;
			case "BGN":
					amount *= EUR_TO_BGN;
					break;
			}
		}
		
		if(currencySelected.equals("$")){
			switch (currency) {
			case "&euro":
					amount *= DOLLAR_TO_EUR;
					break;
			case "BGN":
					amount *= DOLLAR_TO_BGN;
					break;
			}
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format(amount));
	}
	
}