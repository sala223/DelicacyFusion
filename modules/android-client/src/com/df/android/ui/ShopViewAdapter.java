package com.df.android.ui;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.df.android.entity.Dish;
import com.df.android.entity.Item;
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Shop;
import com.df.android.entity.Table;
import com.df.android.menu.Menu;

public class ShopViewAdapter {
	private Shop shop = null;
	ShopChangeListener shopChangeListener;
	Context cxt;

	public ShopViewAdapter(Context cxt) {
		this.cxt = cxt;
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					String shopId = bundle.getString("SHOPID");
//					int resultCode = bundle.getInt("RESULT");
//					if (resultCode == Activity.RESULT_OK) {
						updateShop(shopId);
						shopChangeListener.onChange(shop);
//					} 
				}
			}
		};
		cxt.registerReceiver(receiver, new IntentFilter("SHOP_SYNC"));
	}

	public void setOnShopChangeListener(ShopChangeListener listener) {
		this.shopChangeListener = listener;
	}

	public Shop getShop() {
		return shop;
	}

	private void updateShop(String shopId) {
		if (shop == null)
			shop = new Shop(shopId);

		// Load menus
		Menu menu = buildMenuFromMetaFile(cxt.getExternalCacheDir() + "/" + shopId + "/menu.xml");
		shop.setMenu(menu);

		// Load tables
		List<Table> tables = buildTablesFromMetaFile(cxt.getExternalCacheDir() + "/" + shopId
				+ "/tables.xml");

		shop.setTables(tables);

		// Load default configurations
		initShopConfigurations(shop);
	}

	private void initShopConfigurations(final Shop shop) {
		String metaFile = cxt.getExternalCacheDir() + "/" + shop.getId() + "/shop.xml";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse("file://" + metaFile);
			Element root = dom.getDocumentElement();
			NodeList confNodes = root.getChildNodes();
			for (int i = 0; i < confNodes.getLength(); i++) {
				if ("menuCategories".equals(confNodes.item(i).getNodeName())) {
					List<ItemCategory> categories = new ArrayList<ItemCategory>();

					NodeList xmlItems = root.getElementsByTagName("category");
					for (int j = 0; j < xmlItems.getLength(); j++) {
						categories.add(ItemCategory.valueOf(xmlItems.item(j)
								.getTextContent()));
					}

					shop.setNavigatableMenuItemCategories(categories);
				}
			}
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to initialize shop configurations due to " + e);
		}
	}

	private Menu buildMenuFromMetaFile(String metaFile) {
		Menu menu = new Menu();

		Log.d(getClass().getName(), "Building menu from " + metaFile);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse("file://" + metaFile);
			Element root = dom.getDocumentElement();
			NodeList xmlItems = root.getElementsByTagName("item");

			Log.d(getClass().getName(), "Found " + xmlItems.getLength()
					+ " items");

			for (int i = 0; i < xmlItems.getLength(); i++) {
				Element xmlItem = (Element) xmlItems.item(i);
				String id = xmlItem.getAttribute("id");
				String name = xmlItem.getAttribute("name");
				Item item = new Dish(id, name);

				String image = xmlItem.getAttribute("image");
				float price = Float.parseFloat(xmlItem.getAttribute("price"));
				item.setPrice(price);
				item.setImage(image);

				NodeList xmlCategories = xmlItem
						.getElementsByTagName("category");
				for (int j = 0; j < xmlCategories.getLength(); j++) {
					item.addCategory(ItemCategory.valueOf(xmlCategories.item(j)
							.getTextContent()));
				}

				menu.addItem(item);
			}
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load menu from meta file due to " + e);
		}

		return menu;
	}
	
	private List<Table> buildTablesFromMetaFile(String metaFile) {
		List<Table> tables = new ArrayList<Table>();

		Log.i(getClass().getName(), "Building tables from " + metaFile);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse("file://" + metaFile);
			Element root = dom.getDocumentElement();
			NodeList xmlItems = root.getElementsByTagName("table");

			Log.d(getClass().getName(), "Found " + xmlItems.getLength()
					+ " tables");

			for (int i = 0; i < xmlItems.getLength(); i++) {
				Element xmlItem = (Element) xmlItems.item(i);
				String id = xmlItem.getAttribute("id");
				int capacity = Integer.parseInt(xmlItem
						.getAttribute("capacity"));

				tables.add(new Table(id, capacity));
			}
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load menu from meta file due to " + e);
		}

		return tables;
	}
}