package com.model;

public class Adress {
	private int adressID = 0, userID = 0;
	private String name = null;
	private String tel = null;
	private String postcode = null;
	private String province = null;
	private String city = null;
	private String district = null;
	private String detailAddress = null;

	public Adress() {
		super();
	}

	public Adress(int userID, String name, String tel, String postcode,
			String province, String city, String district, String detailAddress) {
		super();
		this.userID = userID;
		this.name = name;
		this.tel = tel;
		this.postcode = postcode;
		this.province = province;
		this.city = city;
		this.district = district;
		this.detailAddress = detailAddress;
	}

	public int getAdressID() {
		return adressID;
	}

	public void setAdressID(int adressID) {
		this.adressID = adressID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

}
