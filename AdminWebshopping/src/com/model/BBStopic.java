package com.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BBStopic {
	private int topicID=0;
	private String title = null;
	private int sectionID=0;
	private String sectionName=null;
	private int userID=0;
	private boolean on_the_top = false;
	private  String modifyTimeDate = null;
	private  String publishTimeDate = null;
	private String content = null;
	private int view_count=0;
	private int num_of_reply=0;
	private String user_nameString=null;
	private String IconURL=null;
	private int id=0;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public void setModifyTimeDate(String modifyTimeDate) {
		this.modifyTimeDate = modifyTimeDate;
	}
	public void setPublishTimeDate(String publishTimeDate) {
		this.publishTimeDate = publishTimeDate;
	}
	public String getIconURL() {
		return IconURL;
	}
	public void setIconURL(String iconURL) {
		IconURL = iconURL;
	}
	public String getUser_nameString() {
		return user_nameString;
	}
	public void setUser_nameString(String user_nameString) {
		this.user_nameString = user_nameString;
	}
	public int getTopicID() {
		return topicID;
	}
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSectionID() {
		return sectionID;
	}
	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public boolean isOn_the_top() {
		return on_the_top;
	}
	public void setOn_the_top(boolean on_the_top) {
		this.on_the_top = on_the_top;
	}
	public String getModifyTimeDate() {
		return modifyTimeDate;
	}
	public void setModifyTimeDate(Date modifyTimeDate) {
		this.modifyTimeDate = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
				.format(modifyTimeDate);
	}
	public String getPublishTimeDate() {
		return publishTimeDate;
	}
	public void setPublishTimeDate(Date publishTimeDate) {
		this.publishTimeDate = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
				.format(publishTimeDate);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public int getNum_of_reply() {
		return num_of_reply;
	}
	public void setNum_of_reply(int num_of_reply) {
		this.num_of_reply = num_of_reply;
	}
	
	
	
	
	

	

}
