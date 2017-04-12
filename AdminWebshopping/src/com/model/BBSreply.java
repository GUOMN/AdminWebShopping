package com.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BBSreply {
	private int replyID=0;
	private int topicID=0;
	private int userID=0;
	private String title = null;
	private  String modifyTime = null;
	private  String publishTimeDate = null;
	private String content = null;
	private String user_nameString=null;
	private String IconURL=null;
	private int id=0;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public void setPublishTimeDate(String publishTimeDate) {
		this.publishTimeDate = publishTimeDate;
	}
	@Override
	public String toString() {
		return "BBSreply [replyID=" + replyID + ", topicID=" + topicID
				+ ", userID=" + userID + ", title=" + title + ", modifyTime="
				+ modifyTime + ", publishTimeDate=" + publishTimeDate
				+ ", contet=" + content + ", user_nameString=" + user_nameString
				+ ", IconURL=" + IconURL + "]";
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
	public int getReplyID() {
		return replyID;
	}
	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}
	public int getTopicID() {
		return topicID;
	}
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
				.format(modifyTime);
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


}
