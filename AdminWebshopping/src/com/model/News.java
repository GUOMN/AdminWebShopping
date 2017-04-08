package com.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class News {

	private Integer NewsId;
	private Integer SortId;
	private Integer numhit;
	private Integer deldete;
	private String SortName;
	private String author;
	private String title;
	private String News_content;
	private String keywords;
	private  String date;
	private String ImageURL=null;

	@Override
	public String toString() {
		return "News [NewsId=" + NewsId + ", SortId=" + SortId + ", numhit="
				+ numhit + ", deldete=" + deldete + ", SortName=" + SortName
				+ ", author=" + author + ", title=" + title + ", News_content="
				+ News_content + ", keywords=" + keywords + ", date=" + date
				+ ", ImageURL=" + ImageURL + "]";
	}

	public String getImageURL() {
		return ImageURL;
	}

	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}

	public Integer getNewsId() {
		return NewsId;
	}

	public void setNewsId(Integer newsId) {
		NewsId = newsId;
	}

	public Integer getSortId() {
		return SortId;
	}

	public void setSortId(Integer sortId) {
		SortId = sortId;
	}

	public Integer getNumhit() {
		return numhit;
	}

	public void setNumhit(Integer numhit) {
		this.numhit = numhit;
	}

	public Integer getDeldete() {
		return deldete;
	}

	public void setDeldete(Integer deldete) {
		this.deldete = deldete;
	}

	public String getSortName() {
		return SortName;
	}

	public void setSortName(String sortName) {
		SortName = sortName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNews_content() {
		return News_content;
	}

	public void setNews_content(String news_content) {
		News_content = news_content;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDate() {
		return  date;
	}

	public void setDate(Date date) {
		this.date = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
				.format(date);
	}

}
