package com.model;

public class User {
	private int userID;
	private int id=0;
	private String name = null;
	private String sex = null;
	private String loginName = null;
	private String loginPW = null;
	private String age = null;
	private int DefaultAddressID = 0;
	private String tel = null;
	private String email = null;
	private String image = null;
	private String del = null;
	private int subscribeNews = 0, subscribeOrderStatus = 0;
	private String remark = null;
	private boolean IsAdmin=false;

	
	
	public User() {
		super();
	}

	public User(int userID, String name, String sex, String loginName,
			String loginPW, String age, String tel, String email) {
		super();
		this.userID = userID;
		this.name = name;
		this.sex = sex;
		this.loginName = loginName;
		this.loginPW = loginPW;
		this.age = age;
		this.tel = tel;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIsAdmin() {
		return IsAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		IsAdmin = isAdmin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDefaultAddressID() {
		return DefaultAddressID;
	}

	public void setDefaultAddressID(int defaultAddressID) {
		DefaultAddressID = defaultAddressID;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getSubscribeNews() {
		return subscribeNews;
	}

	public void setSubscribeNews(int subscribeNews) {
		this.subscribeNews = subscribeNews;
	}

	public int getSubscribeOrderStatus() {
		return subscribeOrderStatus;
	}

	public void setSubscribeOrderStatus(int subscribeOrderStatus) {
		this.subscribeOrderStatus = subscribeOrderStatus;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPW() {
		return loginPW;
	}

	public void setLoginPW(String loginPW) {
		this.loginPW = loginPW;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return image;
	}

	public void setQq(String qq) {
		this.image = qq;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

}
