package com.example.dailyexpenses;

public class Wish {
	
	private long id;
	private String name;
	private double amount;
	private double wishRating;
	
	public Wish(double amount, String name,double wishRating){
		
		this.amount = amount;
		this.name = name;
		this.wishRating = wishRating;
	}

	public Wish(double amount, String name,double wishRating, long id){
		this.id = id;
		this.amount = amount;
		this.name = name;
		this.wishRating = wishRating;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getWishRating() {
		return wishRating;
	}

	public void setWishRating(double wishRating) {
		this.wishRating = wishRating;
	}

	public long getWishId() {
		return id;
	}

	public void setWishId(int wishId) {
		this.id = wishId;
	}

	@Override
	public String toString() {
		return String.format(getName() + " " + getAmount() + " "  + getWishRating() + " " + getWishId());
	}


}
