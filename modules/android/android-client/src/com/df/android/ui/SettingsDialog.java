package com.df.android.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.service.StoreSyncService;
import com.df.android.utils.CacheMgr;

public class SettingsDialog extends Dialog {
	SharedPreferences preferences;
	BroadcastReceiver syncShopReceiver = null;

	public SettingsDialog(final Context cxt) {
		super(cxt);
		setContentView(R.layout.settings);
		setTitle(R.string.settings);

		init(cxt);
	}

	private void init(final Context cxt) {
		final EditText etServerAddr = (EditText)findViewById(R.id.serverAddr);
		preferences = PreferenceManager.getDefaultSharedPreferences(cxt); 
		etServerAddr.setText(preferences.getString("SERVER_URL", "http://www.delicacyfusion.com"));
		((TextView)findViewById(R.id.userCode)).setText(GlobalSettings.instance().getUserCode());
		((TextView)findViewById(R.id.tenantName)).setText(preferences.getString("TENANT_CODE", ""));
		((TextView)findViewById(R.id.storeName)).setText(preferences.getString("STORE_CODE", ""));

		findViewById(R.id.btnSyncShop)
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if(syncShopReceiver == null) {
					syncShopReceiver = new BroadcastReceiver() {
						@Override
						public void onReceive(Context context, Intent intent) {
							Bundle bundle = intent.getExtras();
							if (bundle != null) {
								String shopId = bundle.getString("SHOPID");
								Log.d(getClass().getName(), "Received a notification from sync service " + shopId);
							}
						}
					};
					
					cxt.registerReceiver(syncShopReceiver, new IntentFilter("SHOP_SYNC"));
				}
				
				view.getContext().startService(new Intent(view.getContext(), StoreSyncService.class));
			}
		});

		findViewById(R.id.btnCleanCache)
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if(CacheMgr.instance().cleanCache(GlobalSettings.instance().getCurrentTenantCode(), 
						GlobalSettings.instance().getCurrentStoreCode())) {
				}
			}
		});
		
		findViewById(R.id.btnLogout)
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				GlobalSettings.instance().setUserCode(null);
				GlobalSettings.instance().setCurrentStore(null);
				GlobalSettings.instance().setCurrentStoreCode(null);
				GlobalSettings.instance().setCurrentTenantCode(null);
			}
		});
		
		findViewById(R.id.btnConfirmSettings)
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				applySettings();
				dismiss();
			}
		});
	}

	private void applySettings() {
		Editor edit = preferences.edit();

		String serverUrl = ((EditText) findViewById(R.id.serverAddr)).getText()
				.toString();
		edit.putString("SERVER_URL", serverUrl);
		edit.apply();

		GlobalSettings.instance().setServerUrl(serverUrl);
	}
}
