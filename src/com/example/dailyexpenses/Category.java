package com.example.dailyexpenses;

public class Category {

	private long categoryId;
	private String name;
	private double amount;
	
	public Category() {
	}
	
	public Category(String name) {
		this.name = name;
		setAmount(0);
		setCategory_id(0);
	}
	
	public Category(String name, int categoryId) {
		this.name = name;
		this.categoryId = categoryId;
	}
	
	public Category(String name, int categoryId, double amount){
		this.name = name;
		this.categoryId = categoryId;
		this.amount = amount;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getCategory_id() {
		return categoryId;
	}

	public void setCategory_id(long categoryId) {
		this.categoryId = categoryId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return String.format("Name %s, Id %d, Amount %.2f", getName(), getCategory_id(), getAmount());
	}
}
