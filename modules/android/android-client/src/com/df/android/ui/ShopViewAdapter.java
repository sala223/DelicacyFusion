package com.df.android.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.df.android.GlobalSettings;
import com.df.android.entity.Floor;
import com.df.android.entity.Item;
import com.df.android.entity.ItemCategory;
import com.df.android.menu.Menu;
import com.df.client.rs.model.DiningTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ShopViewAdapter {
	ShopChangeListener shopChangeListener;
	Context cxt;

	public ShopViewAdapter(Context cxt) {
		this.cxt = cxt;

		updateShop();

		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					// String shopId = bundle.getString("SHOPID");
					// int resultCode = bundle.getInt("RESULT");
					// if (resultCode == Activity.RESULT_OK) {
					updateShop();
					shopChangeListener.onChange(GlobalSettings.instance()
							.getCurrentStore());
					// }
				}
			}
		};
		cxt.registerReceiver(receiver, new IntentFilter("SHOP_SYNC"));
	}

	public void setOnShopChangeListener(ShopChangeListener listener) {
		this.shopChangeListener = listener;
	}

	private void updateShop() {
		String shopId = GlobalSettings.instance().getClient().getStoreCode();

		try {
			// Load default configurations
			loadConfigurations();

			// Load menus
			loadMenu(shopId);

			// Load tables
			loadTables(shopId);

		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to update shop due to " + ex);
		}
	}
	
	private void loadConfigurations() {
		Log.d(getClass().getName(), "Loading configuration ...");
		
		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							new File(cxt.getExternalCacheDir() + "/"
									+ GlobalSettings.instance().getCurrentStore().getCode()
									+ "/configuration.json"))));
			Type type = new TypeToken<List<String>>() {
			}.getType();
			List<String> catCodes = json.fromJson(reader, type);

			List<ItemCategory> categories = new ArrayList<ItemCategory>();
			for (String code : catCodes) {
				ItemCategory category = GlobalSettings.instance().getCategoryDictionary()
						.getCategoryByCode(code);
				if (category != null)
					categories.add(category);
			}
			GlobalSettings.instance().getCurrentStore()
					.setNavigatableMenuItemCategories(categories);
			
			Log.d(getClass().getName(), "Configuration loaded");
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load configurations due to " + e);
		}
	}

	private void loadMenu(String shopId) {
		Log.d(getClass().getName(), "Loading menu ...");

		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							new File(cxt.getExternalCacheDir() + "/"
									+ shopId + "/menu.json"))));
			Type type = new TypeToken<List<Item>>() {
			}.getType();
			List<Item> items = json.fromJson(reader, type);
			Menu menu = GlobalSettings.instance().getCurrentStore().getMenu();
			for (Item item : items)
				menu.addItem(item);
			
			Log.d(getClass().getName(), "Menu loaded");
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load menu due to " + e);
		}
	}
	
	private void loadTables(String shopId) {
		Log.d(getClass().getName(), "Loading table ...");

		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							new File(cxt.getExternalCacheDir() + "/"
									+ shopId + "/table.json"))));
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
				GlobalSettings.instance().getCurrentStore().getFloors().add(floor);
			}

			Log.d(getClass().getName(), "Tables loaded");
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load table due to " + e);
		}
	}
}