package com.example.dailyexpenses;

public class Record {
	
	private int record_id;
	private double amount;
	private long date;
	private boolean isIncome;
	private String description;
	private long categoryID;
	

	public Record(double amount, long date, boolean isIncome,String description,long categoryID) {

		this.amount = amount;
		this.date = date;
		this.isIncome = isIncome;
		this.description = description;
		this.categoryID = categoryID;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public boolean isIncome() {
		return isIncome;
	}
	
	public void setIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}

	public long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format(getAmount() + " " + getDate() + " " + isIncome() + " " + getCategoryID() + " " +
			  getDescription());
	}

	public int getRecord_id() {
		return record_id;
	}

	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
}
