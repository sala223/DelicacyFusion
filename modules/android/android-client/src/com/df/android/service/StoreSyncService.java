package com.df.android.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import com.df.android.utils.CacheMgr;
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
				syncShop();

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

					CacheMgr.instance().saveCategoryDictionary(categoryDictionary);
					// syncLatch.countDown();
					Log.d(getClass().getName(), "Category dictionary updated");

					return Integer.valueOf(categoryDictionary.size());
				}
			}.execute();
		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to retrieve store");
		}

	}

	private void syncConfiguration() {
		try {
			new WebTask<Integer>() {
				@Override
				protected Integer doInBackground(String... params) {
					Log.d(getClass().getName(), "Retrieving configuration ...");
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

							CacheMgr.instance().saveConfiguration(cateCodes);

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

					CacheMgr.instance().saveItems(cItems);
					// syncLatch.countDown();
					Log.d(getClass().getName(), "countdown in item sync");

					return Integer.valueOf(cItems.size());
				}

			}.execute();
		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to retrieve store");
		}
	}

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
					CacheMgr.instance().saveImage(new ByteArrayInputStream(bytes), sItem.getCode() + "." + pic.getFormat());
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
							CacheMgr.instance().saveTables(tables);

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

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
