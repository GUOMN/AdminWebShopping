package com.model;

public class NewsSort {

	private Integer SortId;
	private String SortName;

	@Override
	public String toString() {
		return "NewsSort [SortId=" + SortId + ", SortName=" + SortName + "]";
	}

	public Integer getSortId() {
		return SortId;
	}

	public void setSortId(Integer sortId) {
		SortId = sortId;
	}

	public String getSortName() {
		return SortName;
	}

	public void setSortName(String sortName) {
		SortName = sortName;
	}

}
