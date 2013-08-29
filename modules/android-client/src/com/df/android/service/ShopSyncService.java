package com.df.android.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class ShopSyncService extends IntentService {
	public ShopSyncService() {
		super("ShopSyncService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String serverUrl = intent.getStringExtra("SERVERURL");
		String shopId = intent.getStringExtra("SHOPID");

		Log.d(getClass().getName(), "Synchronizing ...");
		int result = syncShop(serverUrl, shopId);
		Intent resIntent = new Intent();
		resIntent.putExtra("SHOPID", shopId);
		resIntent.putExtra("RESULT", result);
	    sendBroadcast(resIntent);
		Log.d(getClass().getName(), "Synchronized");
	}

	private int syncShop(final String serverUrl, final String shopId) {
		return syncURL(serverUrl + "/" + shopId);
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
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
