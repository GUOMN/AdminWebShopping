package com.model;

public class Orderitem {
	private int orderitemID;
	private int orderID;
	private int goodID;
	private int goodsQuantity;
	private String detail = null;
	private String image = null;
	private double price = 0;
	private String name;
	private int id=0;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Orderitem() {
		super();
	}

	public Orderitem(int orderitemID, int orderID, int goodID,
			int goodsQuantity, String detail, double price) {
		super();
		this.orderitemID = orderitemID;
		this.orderID = orderID;
		this.goodID = goodID;
		this.goodsQuantity = goodsQuantity;
		this.detail = detail;
		this.price = price;
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
