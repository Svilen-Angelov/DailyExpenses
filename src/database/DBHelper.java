package database;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.dailyexpenses.Category;
import com.example.dailyexpenses.Record;
import com.example.dailyexpenses.Wish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "expenses";
    // Database drop
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS ";
    
	// Tables names
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_RECORDS = "records";
    private static final String TABLE_WISHLIST = "wishlist";
    
    // Common columns
    private static final String KEY_ID = "_id";
    private static final String COLUMN_CREATED_AT = "created_at";
    
    // Categories columns
    private static final String CATEGORY_NAME = "category_name";
    private static final String CATEGORY_AMOUNT = "category_amount";
    private static final String[] CATEGORY_COLUMNS = {KEY_ID, CATEGORY_NAME, CATEGORY_AMOUNT, COLUMN_CREATED_AT };
    
    // Records columns
    private static final String RECORD_AMOUNT = "record_amount";
    private static final String RECORD_DESCR = "record_descr";
    private static final String RECORD_IS_INCOME = "record_is_income";
    private static final String RECORD_CATEGORY_ID = "record_category";
    private static final String RECORD_DATE = "record_date";
    
    // Wishlist
    private static final String WISH_NAME = "wish_name";
    private static final String WISH_AMOUNT = "wish_amount";
    private static final String WISH_RATING = "wish_rating";
    
    // Table Create Statements
    // Categories create statement
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES +
    		"( " + 
	    		KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    		CATEGORY_NAME + " TEXT, " +
	    		CATEGORY_AMOUNT + " REAL, " +
	    		COLUMN_CREATED_AT + " INTEGER " +
    		" )";
 
    // Records table create statement
    private static final String CREATE_RECORDS_TABLE = "CREATE TABLE "+ TABLE_RECORDS +
    		" ( " + 
	    		KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    		RECORD_AMOUNT + " REAL, " + 
	    		RECORD_DESCR + " TEXT, " + 
	    		RECORD_CATEGORY_ID + " REAL, " + 
	    		RECORD_IS_INCOME + " INTEGER, " +
	    		RECORD_DATE + " INTEGER, " + 
	    		COLUMN_CREATED_AT + " INTEGER " +
    		") ";

    // Wishlist table create statement
    private static final String CREATE_WISHLIST_TABLE = "CREATE TABLE " + TABLE_WISHLIST +
    		"( " + 
	    		KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    		WISH_NAME + " TEXT, " +
	    		WISH_AMOUNT + " REAL, " +
	    		WISH_RATING + " REAL " +
    		" )";
    // Database select
	private static final String ORDER_DESC = " DESC";
	
	// Database
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CATEGORIES_TABLE);
		db.execSQL(CREATE_RECORDS_TABLE);
		db.execSQL(CREATE_WISHLIST_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DATABASE_DROP + TABLE_CATEGORIES);
		db.execSQL(DATABASE_DROP + TABLE_RECORDS);
		db.execSQL(DATABASE_DROP + TABLE_WISHLIST);

		this.onCreate(db);
	}

	// Category
	public void addCategory(Category category) {

		final SQLiteDatabase db = this.getWritableDatabase();
        
        final ContentValues values = new ContentValues();

        values.put(CATEGORY_NAME, category.getName());
        values.put(COLUMN_CREATED_AT, getCurrentTime());

        db.insert(TABLE_CATEGORIES, null, values);
               
		Log.i("res", "InAddCategory idBefore: " + category.toString());
		closeDB();
	}
	
	public void updateCategoryAmount(double amount,long id){
		final SQLiteDatabase db = this.getWritableDatabase();
		
		Log.i("res", "Update Cat on idBefore: " + id);
		ContentValues newValues = new ContentValues();
		
		newValues.put(CATEGORY_AMOUNT, amount);
		
		int i = db.update(TABLE_CATEGORIES, newValues, KEY_ID + " = ?", new String[] { String.valueOf(id)});
		
		Log.i("res", "Update Cat on id: " + i);
		closeDB();
	}
	
	public Category getCategoryByID(long id){
		 
	    // 1. get reference to readable DB
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    // 2. build query
	    Cursor cursor = db.query(TABLE_CATEGORIES, CATEGORY_COLUMNS, KEY_ID + " = ?",
	    		new String[] {String.valueOf(id)}, null, null, null, null);
	 
	    // 3. if we got results get the first one
	    if (cursor != null){
	        cursor.moveToFirst();
	    }
	    // 4. build category object
	    Category category = new Category();
	    category.setCategory_id(Long.parseLong(cursor.getString(0)));
	    category.setName(cursor.getString(1));
	    category.setAmount(cursor.getDouble(cursor.getColumnIndex(CATEGORY_AMOUNT)));
	 
	    //log 
	    Log.i("res", "InDBHelper 198 getCategory("+id+") " + category.toString());
	 
	    // 5. return category
	    return category;
	}
	
	public List<Category> getAllCategoies(boolean sort) {
		final SQLiteDatabase db = this.getWritableDatabase();
		String orderBy = null;
				
		List<Category> categories = new ArrayList<Category>();
	 
		if(sort){
			orderBy = CATEGORY_AMOUNT + ORDER_DESC;
	    }
		
		Cursor cursor = db.query(TABLE_CATEGORIES, null, null, null, null, null, orderBy);
	    
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {

			String name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
			double amount = cursor.getDouble(cursor.getColumnIndex(CATEGORY_AMOUNT));
			int inObjecID = cursor.getInt(cursor.getColumnIndex(KEY_ID));

			Category category = new Category(name, inObjecID, amount);
			
			Log.i("res", "InDBRecordcategoryToString:" + category.toString());
			categories.add(category);
			cursor.moveToNext();
		}
		
		cursor.close();
		closeDB();
			    
	    return categories;
	}
	// Records
	public void addRecord(Record record){
		final SQLiteDatabase db = this.getWritableDatabase();
		
		final ContentValues values = new ContentValues();

		values.put(RECORD_AMOUNT, record.getAmount());
		values.put(RECORD_DESCR, record.getDescription());
		values.put(RECORD_CATEGORY_ID, record.getCategoryID());
		values.put(RECORD_IS_INCOME, convertBoolToInt(record.isIncome()));
		values.put(RECORD_DATE, record.getDate());
		
		values.put(COLUMN_CREATED_AT, getCurrentTime());
		
		db.insert(TABLE_RECORDS, null, values);
		
		closeDB();
	}
	
	public void updateRecord(Record record){
		final SQLiteDatabase db = this.getWritableDatabase();
		
		Log.i("res", "Update Cat on idBefore: " + record.getRecord_id());
		ContentValues newValues = new ContentValues();
		
		newValues.put(RECORD_AMOUNT, record.getAmount());
		newValues.put(RECORD_DESCR, record.getDescription());
		newValues.put(RECORD_CATEGORY_ID, record.getCategoryID());
		newValues.put(RECORD_IS_INCOME, convertBoolToInt(record.isIncome()));
		newValues.put(RECORD_DATE, record.getDate());
		
		db.update(TABLE_RECORDS, newValues, KEY_ID + " = ?", new String[] { String.valueOf(record.getRecord_id())});
		
		Log.i("res", "Update record on id: " + record.getRecord_id());
		closeDB();
	}
	
	public List<Record> getAllRecords() {
		final SQLiteDatabase db = this.getWritableDatabase();
		
		List<Record> records = new ArrayList<Record>();
	 
	    Cursor c = db.query(TABLE_RECORDS, null, null, null, null, null, null); 
	    
		c.moveToFirst();
		while (c.isAfterLast() == false) {

			double amount = c.getDouble(c.getColumnIndex(RECORD_AMOUNT));
			long date = c.getLong(c.getColumnIndex(RECORD_DATE));
			boolean isIncome = convertIntToBol(c.getInt(c.getColumnIndex(RECORD_IS_INCOME)));
			String description = c.getString(c.getColumnIndex(RECORD_DESCR));
			long categoryID = c.getLong(c.getColumnIndex(RECORD_CATEGORY_ID));

			Record record = new Record(amount, date, isIncome, description,categoryID);

			Log.i("res", "InDBRecord:" + record.toString());
			records.add(record);
			c.moveToNext();
		}
	 
	    c.close();
		closeDB();
	    
	    return records;
	}
	
	public List<Record> getRecordsByType(boolean isIncome) {
		final SQLiteDatabase db = this.getWritableDatabase();
		
		List<Record> records = new ArrayList<Record>();
		
		int id = convertBoolToInt(isIncome);
		
	    Cursor c = db.query(TABLE_RECORDS, null, RECORD_IS_INCOME + " = ?",
	    		new String[] {String.valueOf(id)}, null, null, null); 
	    
		c.moveToFirst();
		while (c.isAfterLast() == false) {

			double amount = c.getDouble(c.getColumnIndex(RECORD_AMOUNT));
			long date = c.getLong(c.getColumnIndex(RECORD_DATE));
			String description = c.getString(c.getColumnIndex(RECORD_DESCR));
			long categoryID = c.getLong(c.getColumnIndex(RECORD_CATEGORY_ID));

			Record record = new Record(amount, date, isIncome, description,categoryID);

			Log.i("res", "Get records by type:" + record.toString());
			records.add(record);
			c.moveToNext();
		}
	 
	    c.close();
		closeDB();
	    
	    return records;
	}
	
	public void deleteAllRecords(SQLiteDatabase db) {
        db.execSQL(DATABASE_DROP + TABLE_RECORDS);
        
		db.execSQL(CREATE_RECORDS_TABLE);
	}
	
	// Wish
	
	public void addWish(Wish wish){
		final SQLiteDatabase db = this.getWritableDatabase();
		
		final ContentValues values = new ContentValues();

		values.put(WISH_NAME, wish.getName());
		values.put(WISH_AMOUNT, wish.getAmount());
		values.put(WISH_RATING, wish.getWishRating());
				
		db.insert(TABLE_WISHLIST, null, values);
		
		closeDB();
	}
	
	public void updateWish(Wish wish){
		final SQLiteDatabase db = this.getWritableDatabase();
		
		Log.i("res", "Update wish on id:before " + wish.getWishId());
		ContentValues newValues = new ContentValues();
		
		newValues.put(WISH_NAME, wish.getName());
		newValues.put(WISH_AMOUNT, wish.getAmount());
		newValues.put(WISH_RATING, wish.getWishRating());
		
		db.update(TABLE_WISHLIST, newValues, KEY_ID + " = ?", new String[] { String.valueOf(wish.getWishId())});
		
		Log.i("res", "Update wish on id:after " + wish.getWishId());
		closeDB();
	}
	
	public List<Wish> getAllWishes() {

		Log.i("res", "getAllWishes method, getting db");
		final SQLiteDatabase db = this.getWritableDatabase();

		List<Wish> wishes = new ArrayList<Wish>();
	 
	    Cursor c = db.query(TABLE_WISHLIST, null, null, null, null, null, null); 
	    
		c.moveToFirst();

		Log.i("res", "Before while. ");
		while (c.isAfterLast() == false) {

			Log.i("res", "in while: ");
			long id = c.getLong(c.getColumnIndex(KEY_ID));
			double wishRating = c.getDouble(c.getColumnIndex(WISH_RATING));
			double amount = c.getDouble(c.getColumnIndex(WISH_AMOUNT));
			String name = c.getString(c.getColumnIndex(WISH_NAME));

			Wish wish = new Wish(amount, name, wishRating, id);
			
			Log.i("res", "GetWish:" + wish.toString());
			wishes.add(wish);
			c.moveToNext();
		}

		Log.i("res", "Aftter while: ");
	    c.close();
		closeDB();
	    
	    return wishes;
	}
	
	public void deleteWish(long wishId) {
				
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_WISHLIST, KEY_ID + " = ?", new String[] { String.valueOf(wishId)});
		
		Log.i("res", "Delete wish on id: " + wishId);
		}
	
	// Help funcitions
	private int convertBoolToInt(boolean value){
		if(value){
			return 1;
		}
		return 0;
	}
	
	private boolean convertIntToBol(int value){
		if(value == 1){
			return true;
		}
		return false;
	}

	private long getCurrentTime() {
		Date d = new Date();
		return d.getTime();
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen()){
			db.close();
		}
	}

	public void deleteHistory() {
		final SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_RECORDS, null, null);
	}
}
