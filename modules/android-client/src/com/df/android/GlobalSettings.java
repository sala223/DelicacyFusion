package com.df.android;

import com.df.android.entity.Shop;

public class GlobalSettings {
	private static String serverUrl;
	public static String getServerUrl() {
		return serverUrl;
	}
	
	public static void setServerUrl(String url) {
		serverUrl = url;
	}
	
	private static Shop currentShop = null;
	public static Shop getCurrentShop() {
		return currentShop;
	}

	public static void setCurrentShop(Shop currentShop) {
		GlobalSettings.currentShop = currentShop;
	}

	
}
