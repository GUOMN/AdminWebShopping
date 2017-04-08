package com.model;

public class Item extends Goods {
	private int itemID = 0, handinventory = 0;
	private String introduct = null;
	private String image = null;

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	@Override
	public int getHandinventory() {
		return handinventory;
	}

	@Override
	public void setHandinventory(int handinventory) {
		this.handinventory = handinventory;
	}

	@Override
	public String getIntroduct() {
		return introduct;
	}

	@Override
	public void setIntroduct(String introduct) {
		this.introduct = introduct;
	}

	@Override
	public String getImage() {
		return image;
	}

	@Override
	public void setImage(String image) {
		this.image = image;
	}

}
