package com.model;

public class File_3DModel {
	/**
	 * 3D打印模型文件上传用。 封装上传的文件信息
	 * 
	 * @author 郭梦男 2016-9-25
	 */

	private String ModelName = null;
	private String location = null;
	private String description = null;
	private int UserID = 0;
	private String UploadTime = null;

	public File_3DModel() {
		super();
	}

	public File_3DModel(String modelName, String location, int userID) {
		super();
		ModelName = modelName;
		this.location = location;
		UserID = userID;
	}

	public String getModelName() {
		return ModelName;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getUploadTime() {
		return UploadTime;
	}

	public void setUploadTime(String uploadTime) {
		UploadTime = uploadTime;
	}

}
