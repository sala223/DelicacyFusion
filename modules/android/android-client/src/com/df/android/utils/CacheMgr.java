package com.df.android.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.df.android.GlobalSettings;
import com.df.android.entity.Floor;
import com.df.android.entity.Item;
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Store;
import com.df.android.menu.CategoryDictionary;
import com.df.android.menu.Menu;
import com.df.client.rs.model.DiningTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CacheMgr {
	private Context cxt;

	private static CacheMgr instance;

	private CacheMgr(Context cxt) {
		this.cxt = cxt;
	}

	public static CacheMgr newInstance(final Context cxt) {
		if (instance == null)
			instance = new CacheMgr(cxt);

		return instance;
	}

	public static CacheMgr instance() {
		return instance;
	}

	public String getCacheDir(String tenantCode, String storeCode) {
		return cxt.getExternalCacheDir() + "/" + tenantCode + "/" + storeCode;
	}

	public boolean isStoreCached(String tenantCode, String storeCode) {
		String dir = getCacheDir(tenantCode, storeCode);

		return new File(dir).exists();
	}
	
	public boolean cleanCache(String tenantCode, String storeCode) {
		String rootDir = cxt.getExternalCacheDir() + "/" + tenantCode + "/" + storeCode;
		
		boolean ret = false;
		
		File tempDir = new File(rootDir);
		if (tempDir.exists())
			ret = tempDir.delete();
		
		return ret;
	}

	public void saveStore(String tenantCode, Store store) {
		Log.d(getClass().getName(), "Saving store ...");

		String rootDir = getCacheDir(tenantCode, store.getCode());

		File tempDir = new File(rootDir);
		if (!tempDir.exists())
			tempDir.mkdirs();

		File file = new File(rootDir + "/store.json");

		FileOutputStream fileos = null;
		try {
			file.createNewFile();
			fileos = new FileOutputStream(file);

			Gson json = new Gson();
			String content = json.toJson(store);
			fileos.write(content.getBytes("UTF-8"));
			fileos.close();
			Log.d(getClass().getName(), "Store synced");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to save store due to " + e);
		}
	}

	public Store loadStore(String tenantCode, String storeCode) {
		if (!isStoreCached(tenantCode, storeCode))
			return null;

		Log.d(getClass().getName(), "Loading store ...");

		Store store = null;
		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(new File(getCacheDir(
									tenantCode, storeCode) + "/store.json"))));
			store = json.fromJson(reader, Store.class);

			Log.d(getClass().getName(), "Store loaded");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to load store due to " + e);
		}

		return store;
	}

	public void loadCategoryDictionary() {
		Log.d(getClass().getName(), "Loading category dictionary ...");

		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(
							cxt.getExternalCacheDir() + "/catdic.json"))));

			CategoryDictionary catDic = json.fromJson(reader,
					CategoryDictionary.class);

			GlobalSettings.instance().setCategoryDictionary(catDic);

			Log.d(getClass().getName(), "Category dictionary initialized");
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to initialize dictionary initialized due to " + e);
		}
	}

	public void saveCategoryDictionary(CategoryDictionary dic) {
		Log.d(getClass().getName(), "Saving category dictionary");

		File file = new File(cxt.getExternalCacheDir() + "/catdic.json");

		FileOutputStream fileos = null;
		try {
			file.createNewFile();
			fileos = new FileOutputStream(file);

			Gson json = new Gson();
			String content = json.toJson(dic);
			fileos.write(content.getBytes("UTF-8"));
			fileos.close();
			Log.d(getClass().getName(), "Category dictionary saved");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to save category dictionary due to " + e);
		}
	}

	public void saveConfiguration(List<String> catCodes) {
		Log.d(getClass().getName(), "Saving configuration");

		String rootFolder = getCacheDir(GlobalSettings.instance()
				.getCurrentTenantCode(), GlobalSettings.instance()
				.getCurrentStoreCode());

		File cacheFolder = new File(rootFolder);
		if (!cacheFolder.exists())
			cacheFolder.mkdirs();

		File file = new File(rootFolder + "/configuration.json");

		FileOutputStream fileos = null;
		try {
			file.createNewFile();
			fileos = new FileOutputStream(file);

			Gson json = new Gson();
			String content = json.toJson(catCodes);
			fileos.write(content.getBytes("UTF-8"));
			fileos.close();
			Log.d(getClass().getName(), "Configuration synced");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to save configuration due to " + e);
		}
	}

	private static final long MAX_BYTES = 1024 * 1024;

	public void saveImage(InputStream ifSrc, String tgtFile) throws IOException {
		String rootFolder = getCacheDir(GlobalSettings.instance()
				.getCurrentTenantCode(), GlobalSettings.instance()
				.getCurrentStoreCode())
				+ "/image";

		File cacheFolder = new File(rootFolder);
		if (!cacheFolder.exists())
			cacheFolder.mkdirs();

		Log.d(getClass().getName(), "Save image to " + rootFolder + tgtFile);

		FileOutputStream fos = null;
		FileChannel oc = null;
		try {
			fos = new FileOutputStream(rootFolder + tgtFile);
			oc = fos.getChannel();
			oc.transferFrom(Channels.newChannel(ifSrc), 0, MAX_BYTES);
		} catch (IOException e) {
		} finally {
			if (oc != null)
				oc.close();
			if (fos != null)
				fos.close();
		}
	}

	public void saveItems(List<Item> items) {
		String rootFolder = getCacheDir(GlobalSettings.instance()
				.getCurrentTenantCode(), GlobalSettings.instance()
				.getCurrentStoreCode());

		File cacheFolder = new File(rootFolder);
		if (!cacheFolder.exists())
			cacheFolder.mkdirs();

		File file = new File(rootFolder + "/menu.json");

		FileOutputStream fileos = null;
		try {
			file.createNewFile();
			fileos = new FileOutputStream(file);

			Gson json = new Gson();
			String content = json.toJson(items);
			fileos.write(content.getBytes("UTF-8"));
			fileos.close();
			Log.d(getClass().getName(), "Items updated");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to save items due to " + e);
		}
	}

	public void saveTables(DiningTable[] tables) {
		String rootFolder = getCacheDir(GlobalSettings.instance()
				.getCurrentTenantCode(), GlobalSettings.instance()
				.getCurrentStoreCode());

		File cacheFolder = new File(rootFolder);
		if (!cacheFolder.exists())
			cacheFolder.mkdirs();

		File file = new File(rootFolder + "/table.json");

		FileOutputStream fileos = null;
		try {
			file.createNewFile();
			fileos = new FileOutputStream(file);

			Gson json = new Gson();
			String content = json.toJson(tables);
			fileos.write(content.getBytes("UTF-8"));
			fileos.close();
			Log.d(getClass().getName(), "Tables updated");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to save tables due to " + e);
		}
	}

	public void loadConfigurations() {
		Log.d(getClass().getName(), "Loading configuration ...");

		String rootFolder = getCacheDir(GlobalSettings.instance()
				.getCurrentTenantCode(), GlobalSettings.instance()
				.getCurrentStoreCode());
		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(
							rootFolder + "/configuration.json"))));
			Type type = new TypeToken<List<String>>() {
			}.getType();
			List<String> catCodes = json.fromJson(reader, type);

			List<ItemCategory> categories = new ArrayList<ItemCategory>();
			for (String code : catCodes) {
				ItemCategory category = GlobalSettings.instance()
						.getCategoryDictionary().getCategoryByCode(code);
				if (category != null)
					categories.add(category);
			}
			GlobalSettings.instance().getCurrentStore()
					.setNavigatableMenuItemCategories(categories);

			Log.d(getClass().getName(), "Configuration loaded");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to load configurations due to "
					+ e);
		}
	}

	public void loadMenu() {
		Log.d(getClass().getName(), "Loading menu ...");

		String rootFolder = getCacheDir(GlobalSettings.instance()
				.getCurrentTenantCode(), GlobalSettings.instance()
				.getCurrentStoreCode());
		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(
							rootFolder + "/menu.json"))));
			Type type = new TypeToken<List<Item>>() {
			}.getType();
			List<Item> items = json.fromJson(reader, type);
			Menu menu = GlobalSettings.instance().getCurrentStore().getMenu();
			for (Item item : items)
				menu.addItem(item);

			Log.d(getClass().getName(), "Menu loaded");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to load menu due to " + e);
		}
	}

	public void loadTables() {
		Log.d(getClass().getName(), "Loading table ...");

		String rootFolder = getCacheDir(GlobalSettings.instance()
				.getCurrentTenantCode(), GlobalSettings.instance()
				.getCurrentStoreCode());
		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(
							rootFolder + "/table.json"))));
			Type type = new TypeToken<List<DiningTable>>() {
			}.getType();
			List<DiningTable> tables = json.fromJson(reader, type);

			List<Floor> floors = new ArrayList<Floor>();
			for (int i = 0; i < 3; i++) {
				Floor floor = new Floor();
				floor.setFloor(i + 1);
				floors.add(floor);
			}

			for (Floor floor : floors) {
				for (DiningTable table : tables) {
					floor.addTable(table);
				}
				GlobalSettings.instance().getCurrentStore().getFloors()
						.add(floor);
			}

			Log.d(getClass().getName(), "Tables loaded");
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to load table due to " + e);
		}
	}
}
