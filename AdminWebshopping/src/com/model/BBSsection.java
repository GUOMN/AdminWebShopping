package com.model;

public class BBSsection {
	private int sectionID=0;
	private String section_name = null;
	private int top_topic =0;
	
	
	@Override
	public String toString() {
		return "BBSsection [sectionID=" + sectionID + ", section_name="
				+ section_name + ", top_topic=" + top_topic
				+ ", getSectionID()=" + getSectionID() + ", getSection_name()="
				+ getSection_name() + ", getTop_topic()=" + getTop_topic()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	public BBSsection() {
		super();
	}

	public BBSsection(int sectionID, String section_name, int top_topic) {
		super();
		this.sectionID = sectionID;
		this.section_name = section_name;
		this.top_topic = top_topic;
	}
	public int getSectionID() {
		return sectionID;
	}
	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public int getTop_topic() {
		return top_topic;
	}
	public void setTop_topic(int top_topic) {
		this.top_topic = top_topic;
	}
	



}
