package com.df.android.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.df.android.GlobalSettings;
import com.df.android.entity.Item;
import com.df.android.entity.ItemCategory;
import com.df.android.menu.CategoryDictionary;
import com.df.android.utils.WebTask;
import com.df.client.rs.model.Category;
import com.df.client.rs.model.CategoryNavigationTabs;
import com.df.client.rs.model.CategoryNavigationTabs.CategoryNavigationTab;
import com.df.client.rs.model.DiningTable;
import com.df.client.rs.model.PictureRef;
import com.df.client.rs.resource.CategoryResource;
import com.df.client.rs.resource.ConfigurationResource;
import com.df.client.rs.resource.ItemResource;
import com.df.client.rs.resource.TableResource;
import com.google.gson.Gson;

public class StoreSyncService extends Service {
	// private final static int SYNC_INTERVAL = 10000;
	// final CountDownLatch syncLatch = new CountDownLatch(2);

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		start();
		return Service.START_NOT_STICKY;
	}

	private void start() {
		Log.d(getClass().getName(), "Starting sync service ...");
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				syncCategoryDictionary();
				int result = syncShop();

				// try {
				// Log.d(getClass().getName(), "Waiting for sync finish ...");
				// // syncLatch.await();
				//
				// Log.d(getClass().getName(), "Broadcasting ...");
				// Intent resIntent = new Intent();
				// resIntent.setAction("SHOP_SYNC");
				// resIntent.putExtra("SHOPID", GlobalSettings.instance()
				// .getClient().getStoreCode());
				// resIntent.putExtra("RESULT", result);
				// sendBroadcast(resIntent);
				//
				// // handler.postDelayed(this, SYNC_INTERVAL);
				// } catch (Exception e) {
				// }

			}

		});
		Log.d(getClass().getName(), "Sync service started");
	}

	private int syncShop() {
		syncConfiguration();
		syncItems();
		syncTables();

		return Activity.RESULT_OK;
	}

	private void syncCategoryDictionary() {
		try {
			new WebTask<Integer>() {
				@Override
				protected Integer doInBackground(String... params) {
					Log.d(getClass().getName(), "Retrieving categories ...");

					CategoryResource res = GlobalSettings.instance()
							.getClient().getResource(CategoryResource.class);

					CategoryDictionary categoryDictionary = GlobalSettings
							.instance().getCategoryDictionary();
					try {
						Category[] sCategories = res.getCategories();
						categoryDictionary.clear();
						for (Category sCategory : sCategories) {
							ItemCategory category = new ItemCategory();
							category.setCode(sCategory.getCode());
							category.setName(sCategory.getName());
							categoryDictionary.addCategory(category);
						}
					} catch (Exception e) {
						Log.e(getClass().getName(),
								"Fail to sync category dictionary from server due to "
										+ e);
						return Integer.valueOf(-1);
					}

					saveCategoryDictionary(categoryDictionary);
					// syncLatch.countDown();
					Log.d(getClass().getName(), "Category dictionary updated");

					return Integer.valueOf(categoryDictionary.size());
				}
			}.execute();
		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to retrieve store");
		}

	}

	private void saveCategoryDictionary(CategoryDictionary dic) {
		Log.d(getClass().getName(), "Saving category dictionary");

		File file = new File(getExternalCacheDir() + "/catdic.json");

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
			Log.e(getClass().getName(), "Fail to save category dictionary");
		}
	}

	private void syncConfiguration() {
		try {
			new WebTask<Integer>() {
				@Override
				protected Integer doInBackground(String... params) {
					Log.d(getClass().getName(), "Retrieving categories ...");
					ConfigurationResource res = GlobalSettings.instance()
							.getClient()
							.getResource(ConfigurationResource.class);
					try {
						CategoryNavigationTabs tabs = res
								.getCategoryNavigationTabs();
						if (tabs != null) {
							List<String> cateCodes = new ArrayList<String>();
							for (CategoryNavigationTab tab : tabs.getTabs()) {
								cateCodes.add(tab.getCategoryCode());
							}

							saveConfiguration(cateCodes);

							return Integer.valueOf(cateCodes.size());
						}
					} catch (Exception e) {
						Log.e(getClass().getName(),
								"Fail to sync configuration from server due to "
										+ e);
					}

					return Integer.valueOf(-1);
				}
			}.execute();
		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to run web task due to " + ex);
		}
	}

	private void saveConfiguration(List<String> catCodes) {
		Log.d(getClass().getName(), "Saving configuration");

		String shopId = GlobalSettings.instance().getClient().getStoreCode();
		String rootFolder = getExternalCacheDir() + "/" + shopId;

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
			Log.e(getClass().getName(), "Fail to save configurate");
		}
	}

	private void syncItems() {
		try {
			new WebTask<Integer>() {
				@Override
				protected Integer doInBackground(String... params) {
					ItemResource res = GlobalSettings.instance().getClient()
							.getResource(ItemResource.class);
					com.df.client.rs.model.Item[] sItems;
					try {
						Log.d(getClass().getName(),
								"Retrieving items from server ...");
						sItems = res.getFoods();
						Log.d(getClass().getName(), "Totally " + sItems.length
								+ " items retrieved");

						Log.d(getClass().getName(),
								"Retrieving images from server ...");
						syncImages(res, sItems);
						Log.d(getClass().getName(), "Images retrieved");
					} catch (Exception e) {
						Log.e(getClass().getName(),
								"Fail to retrieve items from server due to "
										+ e);
						return Integer.valueOf(-1);
					}

					List<Item> cItems = new ArrayList<Item>();
					for (com.df.client.rs.model.Item sItem : sItems) {
						Item cItem = new Item();
						cItem.setCode(sItem.getCode());
						cItem.setName(sItem.getName());
						if (sItem.getPictureSet().size() > 0) {
							PictureRef pic = sItem.getPictureSet().toArray(
									new PictureRef[0])[0];
							cItem.setImage(sItem.getCode() + "."
									+ pic.getFormat());
						}

						for (String catCode : sItem.getCategories()) {
							cItem.getCategories().add(catCode);
						}

						cItems.add(cItem);
					}

					saveItems(cItems);
					// syncLatch.countDown();
					Log.d(getClass().getName(), "countdown in item sync");

					return Integer.valueOf(cItems.size());
				}

			}.execute();
		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to retrieve store");
		}
	}

	private static final long MAX_BYTES = 1024 * 1024;

	private void syncImages(ItemResource res,
			com.df.client.rs.model.Item[] sItems) {
		String imageFolder = getExternalCacheDir() + "/"
				+ GlobalSettings.instance().getClient().getStoreCode()
				+ "/image";
		File imgFolder = new File(imageFolder);
		if (!imgFolder.exists())
			imgFolder.mkdirs();

		Log.d(getClass().getName(), "Retrieving images ...");
		for (com.df.client.rs.model.Item sItem : sItems) {
			Log.d(getClass().getName(),
					"Retrieving image from item " + sItem.getCode());
			if (sItem.getPictureSet().size() > 0) {

				PictureRef pic = sItem.getPictureSet().toArray(
						new PictureRef[0])[0];

				InputStream ifSrc = null;
				try {
					byte[] bytes = res.getItemImage(sItem, pic.getImageId());
					saveImage(new ByteArrayInputStream(bytes), imageFolder
							+ "/" + sItem.getCode() + "." + pic.getFormat());
				} catch (Exception e) {
					Log.e(getClass().getName(), "Fail to save image due to "
							+ e);
				} finally {
					try {
						if (ifSrc != null)
							ifSrc.close();
					} catch (IOException e) {
					}
				}
			}
		}
		Log.d(getClass().getName(), "Images retrieved");
	}

	private void saveImage(InputStream ifSrc, String tgtFile)
			throws IOException {
		Log.d(getClass().getName(), "Save image to " + tgtFile);

		FileOutputStream fos = null;
		FileChannel oc = null;
		try {
			fos = new FileOutputStream(tgtFile);
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

	private void saveItems(List<Item> items) {
		String shopId = GlobalSettings.instance().getClient().getStoreCode();
		String rootFolder = getExternalCacheDir() + "/" + shopId;
		Log.d(getClass().getName(), "cache dir: " + rootFolder);

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
			Log.e(getClass().getName(), "Fail to save items");
		}
	}

	private void syncTables() {
		try {
			new WebTask<Integer>() {
				@Override
				protected Integer doInBackground(String... params) {
					Log.d(getClass().getName(), "Retrieving tables ...");
					TableResource res = GlobalSettings.instance()
							.getClient()
							.getResource(TableResource.class);
					try {
						DiningTable[] tables = res.getTables();
							saveTables(tables);

							return Integer.valueOf(tables.length);
					} catch (Exception e) {
						Log.e(getClass().getName(),
								"Fail to sync tables from server due to "
										+ e);
					}

					return Integer.valueOf(-1);
				}
			}.execute();
		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to run web task due to " + ex);
		}
	}

	private void saveTables(DiningTable[] tables) {
		String shopId = GlobalSettings.instance().getClient().getStoreCode();
		String rootFolder = getExternalCacheDir() + "/" + shopId;
		Log.d(getClass().getName(), "cache dir: " + rootFolder);

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
			Log.e(getClass().getName(), "Fail to save tables");
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
