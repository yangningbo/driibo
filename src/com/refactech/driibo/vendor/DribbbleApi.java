package com.refactech.driibo.vendor;

public class DribbbleApi {

	private DribbbleApi() {
	}

	private static final String BASE_URL = "http://api.dribbble.com";
	public static final String SHOTS_LIST = BASE_URL + "/shots/%1$s?page=%2$d";

}
