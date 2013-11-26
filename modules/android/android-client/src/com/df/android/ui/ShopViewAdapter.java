package com.df.android.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.df.android.GlobalSettings;
import com.df.android.utils.CacheMgr;

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
		try {
			// Load default configurations
			CacheMgr.instance().loadConfigurations();

			// Load menus
			CacheMgr.instance().loadMenu();

			// Load tables
			CacheMgr.instance().loadTables();

		} catch (Exception ex) {
			Log.e(getClass().getName(), "Fail to update shop due to " + ex);
		}
	}
	
}