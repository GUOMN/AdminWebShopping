package com.model;

public class Goods {
	protected int goodID;
	private int categoryID;// 分类ID
	private String sex = null;
	private String number = null;// 编号
	private String name = null;
	private String introduct = null;// 简介
	private String material = null;// 商标
	private String attachment = null;// 附件
	private String color = null;// 价格状态（特价？平价？）
	private String del = null;
	private String image = null;// 图片（url）
	private int handinventory;// 库存
	private double priceCommon;// 平价价格
	private double priceSpecial;// 特价价格
	private String modelFilelocation = null;

	public String getModelFilelocation() {
		return modelFilelocation;
	}

	public void setModelFilelocation(String modelFilelocation) {
		this.modelFilelocation = modelFilelocation;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getGoodID() {
		return goodID;
	}

	public void setGoodID(int goodID) {
		this.goodID = goodID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduct() {
		return introduct;
	}

	public void setIntroduct(String introduct) {
		this.introduct = introduct;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public int getHandinventory() {
		return handinventory;
	}

	public void setHandinventory(int handinventory) {
		this.handinventory = handinventory;
	}

	public double getPriceCommon() {
		return priceCommon;
	}

	public void setPriceCommon(double priceCommon) {
		this.priceCommon = priceCommon;
	}

	public double getPriceSpecial() {
		return priceSpecial;
	}

	public void setPriceSpecial(double priceSpecial) {
		this.priceSpecial = priceSpecial;
	}

}
