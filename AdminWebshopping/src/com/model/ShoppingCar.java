package com.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShoppingCar {
	private int ShoppingCarID = 0, goodID = 0, userID = 0, amount = 0;
	private String color = null, material = null, state = null;
	// private Date addedTime = null;
	// 用String代替，方便处理
	private double price = 0;
	private String name = null;
	private String addedTime = null;

	public double getPrice() {
		return price;
	}

	public void setPrice(double d) {
		this.price = d;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getShoppingCarID() {
		return ShoppingCarID;
	}

	public void setShoppingCarID(int shoppingCarID) {
		ShoppingCarID = shoppingCarID;
	}

	public int getGoodID() {
		return goodID;
	}

	public void setGoodID(int goodID) {
		this.goodID = goodID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// public Date getAddedTimeDATE() {
	// return new Date(addedTime);
	// }
	public void setAddedTime(Date addedTime) {
		// this.addedTime = addedTime.toString();
		this.addedTime = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
				.format(addedTime);

	}

	public String getAddedTime() {
		return addedTime;
	}

}
