﻿package com.tool;

import java.util.Map;

public class SaveStateTool {
	private static Map map = null;
	private static String singleString = null;

	public static Map getMap() {
		return map;
	}

	public static void setMap(Map map) {
		SaveStateTool.map = map;
	}

	public static String getSingleString() {
		return singleString;
	}

	public static void setSingleString(String singleString) {
		SaveStateTool.singleString = singleString;
	}

}
