package com.df.android;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.df.android.entity.Store;
import com.df.android.menu.CategoryDictionary;
import com.df.client.http.DFClient;

public class GlobalSettings {
	private static GlobalSettings instance = null;
	private Context cxt;
	private CategoryDictionary categoryDictionary = new CategoryDictionary();

	public CategoryDictionary getCategoryDictionary() {
		return categoryDictionary;
	}
	
	public void setCategoryDictionary(CategoryDictionary categoryDictionary) {
		this.categoryDictionary = categoryDictionary;
	}

	private GlobalSettings(Context cxt) {
		this.cxt = cxt;
	}

	public static GlobalSettings newInstance(Context cxt) {
		return instance = new GlobalSettings(cxt);
	}

	public static GlobalSettings instance() {
		return instance;
	}

	private String serverUrl = null;

	public String getServerUrl() {
		if (serverUrl == null)
			serverUrl = readServerUrlFromPersistence();

		return serverUrl;
	}

	private String readServerUrlFromPersistence() {
		return PreferenceManager.getDefaultSharedPreferences(cxt).getString(
				"SERVERURL", "http://www.delicacyfusion.com");
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
		applySetting("SERVERURL", serverUrl);
	}

	private String userCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	private String tenantCode;

	public String getCurrentTenantCode() {
		return tenantCode;
	}

	public void setCurrentTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
		applySetting("TENANT_CODE", tenantCode);
	}

	private Store currentStore = null;

	public Store getCurrentStore() {
		return currentStore;
	}

	public void setCurrentStore(Store currentStore) {
		this.currentStore = currentStore;
		applySetting("STORE_CODE", currentStore.getCode());
	}

	private DFClient client;

	public DFClient getClient() {
		return client;
	}

	public void setClient(DFClient client) {
		this.client = client;
	}

	private void applySetting(String key, String value) {
		Editor edit = PreferenceManager.getDefaultSharedPreferences(cxt).edit();
		edit.putString(key, value);
		edit.apply();
	}
}
