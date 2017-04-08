﻿package com.model;

public class Orderitem {
	private int orderitemID;
	private int orderID;
	private int goodID;
	private int goodsQuantity;
	private String detail = null;
	private String name = null;
	private String image = null;
	private double price = 0;

	public Orderitem() {
		super();
	}

	public Orderitem(int orderitemID, int goodID, int goodsQuantity,
			String detail) {
		super();
		this.goodID = goodID;
		this.goodsQuantity = goodsQuantity;
		this.detail = detail;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getOrderitemID() {
		return orderitemID;
	}

	public void setOrderitemID(int orderitemID) {
		this.orderitemID = orderitemID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getGoodID() {
		return goodID;
	}

	public void setGoodID(int goodID) {
		this.goodID = goodID;
	}

	public int getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(int goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

}
