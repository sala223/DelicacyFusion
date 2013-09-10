package com.df.android.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;

import com.df.android.GlobalSettings;
import com.df.android.entity.Shop;

public class ShopSyncService extends IntentService {
	private final static int SYNC_INTERVAL = 10000;

	public ShopSyncService() {
		super("ShopSyncService");
		start();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(getClass().getName(), "Starting sync service");

	}

	private void start() {
		final Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				final Shop shop = GlobalSettings.getCurrentShop();
				if (shop == null) {
					Log.d(getClass().getName(),
							"No shop is specified for synchronization");
				} else {
					Log.d(getClass().getName(), "Synchronizing shop " + shop
							+ " ...");
					int result = syncShop(shop.getId());
					Intent resIntent = new Intent();
					resIntent.setAction("SHOP_SYNC");
					resIntent.putExtra("SHOPID", shop.getId());
					resIntent.putExtra("RESULT", result);
					sendBroadcast(resIntent);
					Log.d(getClass().getName(), "Synchronized");
				}

				// handler.postDelayed(this, SYNC_INTERVAL);
			}

		});
	}

	private int syncShop(final String shopId) {
		// return syncURL(GlobalSettings.getServerUrl() + "/" + shopId);
		syncMenu(shopId);
		syncTable(shopId);
		syncConfiguration(shopId);

		return Activity.RESULT_OK;
	}

	private int syncURL(final String url) {
		String fileName = url;
		File output = new File(Environment.getExternalStorageDirectory(),
				fileName);
		if (output.exists()) {
			output.delete();
		}

		InputStream stream = null;
		FileOutputStream fos = null;
		try {

			stream = new URL(url).openConnection().getInputStream();
			InputStreamReader reader = new InputStreamReader(stream);
			fos = new FileOutputStream(output.getPath());
			int next = -1;
			while ((next = reader.read()) != -1) {
				fos.write(next);
			}
			// Successful finished
			return Activity.RESULT_OK;

		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to sync " + url);
			return Activity.RESULT_CANCELED;
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					Log.e(getClass().getName(), "Fail to close file handle");
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					Log.e(getClass().getName(), "Fail to close file handle");
				}
			}
		}
	}

	private void syncConfiguration(final String shopId) {
		String rootFolder = getExternalCacheDir() + "/" + shopId;

		File cacheFolder = new File(rootFolder);
		if (!cacheFolder.exists())
			cacheFolder.mkdirs();

		File menuFile = new File(rootFolder + "/shop.xml");
		Log.d(getClass().getName(), "Sync configuration from " + rootFolder
				+ "/shop.xml");

		try {
			menuFile.createNewFile();
		} catch (IOException e) {
			Log.e("IOException",
					"exception in createNewFile() method " + e.getMessage());
		}

		FileOutputStream fileos = null;
		try {
			fileos = new FileOutputStream(menuFile);
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", "can't create FileOutputStream");
		}
		// we create a XmlSerializer in order to write xml data
		XmlSerializer serializer = Xml.newSerializer();
		try {
			// we set the FileOutputStream as output for the serializer, using
			// UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			serializer.setFeature(
					"http://xmlpull.org/v1/doc/features.html#indent-output",
					true);
			// start a tag called "root"
			serializer.startTag(null, "shop");
			serializer.startTag(null, "menuCategories");
			String categories[] = { "All", "CoolDish", "HotDish", "Soup",
					"Desert" };
			for (String category : categories) {
				serializer.startTag(null, "category");
				serializer.text(category);
				serializer.endTag(null, "category");
			}
			serializer.endTag(null, "menuCategories");
			serializer.endTag(null, "shop");
			serializer.endDocument();
			// write xml data into the FileOutputStream
			serializer.flush();
			// finally we close the file stream
			fileos.close();
			Log.d(getClass().getName(), "Configuration synced");
		} catch (Exception e) {
			Log.e("Exception", "error occurred while creating xml file");
		}
	}

	private void syncMenu(final String shopId) {
		String rootFolder = getExternalCacheDir() + "/" + shopId;
		Log.d(getClass().getName(), "cache dir: " + rootFolder);

		File cacheFolder = new File(rootFolder);
		if (!cacheFolder.exists())
			cacheFolder.mkdirs();

		File menuFile = new File(rootFolder + "/menu.xml");
		Log.d(getClass().getName(), "Sync menu ... " + rootFolder + "/menu.xml");

		try {
			menuFile.createNewFile();
		} catch (IOException e) {
			Log.e("IOException",
					"exception in createNewFile() method " + e.getMessage());
		}

		FileOutputStream fileos = null;
		try {
			fileos = new FileOutputStream(menuFile);
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", "can't create FileOutputStream");
		}
		// we create a XmlSerializer in order to write xml data
		XmlSerializer serializer = Xml.newSerializer();
		try {
			// we set the FileOutputStream as output for the serializer, using
			// UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			serializer.setFeature(
					"http://xmlpull.org/v1/doc/features.html#indent-output",
					true);
			// start a tag called "root"
			serializer.startTag(null, "menu");
			serializer.startTag(null, "item");
			serializer.attribute(null, "id", "D00001");
			serializer.attribute(null, "name", "D00001");
			serializer.attribute(null, "type", "0");
			serializer.attribute(null, "price", "10.00");
			serializer.attribute(null, "image", "image/1.jpg");
			serializer.startTag(null, "category");
			serializer.text("CoolDish");
			serializer.endTag(null, "category");
			serializer.endTag(null, "item");
			serializer.endTag(null, "menu");
			serializer.endDocument();
			// write xml data into the FileOutputStream
			serializer.flush();
			// finally we close the file stream
			fileos.close();
			Log.d(getClass().getName(), "Menu synced");
		} catch (Exception e) {
			Log.e("Exception", "error occurred while creating xml file");
		}
	}

	private void syncTable(final String shopId) {
		String rootFolder = getExternalCacheDir() + "/" + shopId;

		File cacheFolder = new File(rootFolder);
		if (!cacheFolder.exists())
			cacheFolder.mkdirs();

		File menuFile = new File(rootFolder + "/tables.xml");

		try {
			menuFile.createNewFile();
		} catch (IOException e) {
			Log.e("IOException",
					"exception in createNewFile() method " + e.getMessage());
		}

		FileOutputStream fileos = null;
		try {
			fileos = new FileOutputStream(menuFile);
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", "can't create FileOutputStream");
		}
		// we create a XmlSerializer in order to write xml data
		XmlSerializer serializer = Xml.newSerializer();
		try {
			// we set the FileOutputStream as output for the serializer, using
			// UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			serializer.setFeature(
					"http://xmlpull.org/v1/doc/features.html#indent-output",
					true);
			// start a tag called "root"
			serializer.startTag(null, "tables");
			for (int i = 1; i < 20; i++) {
				serializer.startTag(null, "table");
				serializer.attribute(null, "id", "" + i);
				serializer.attribute(null, "capacity", "" + i);
				serializer.endTag(null, "table");
			}
			serializer.endTag(null, "tables");
			serializer.endDocument();
			// write xml data into the FileOutputStream
			serializer.flush();
			// finally we close the file stream
			fileos.close();
			Log.d(getClass().getName(), "Table synced");
		} catch (Exception e) {
			Log.e("Exception", "error occurred while creating xml file");
		}
	}
}
