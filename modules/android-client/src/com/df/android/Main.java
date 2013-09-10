package com.df.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;

import com.df.android.entity.Shop;
import com.df.android.service.ShopSyncService;
import com.df.android.ui.SettingsDialog;

public class Main extends Activity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GlobalSettings.setCurrentShop(new Shop("demo"));
		
		Log.i(getClass().getName(), "Starting sync service");
		// Start sync service
		Intent i = new Intent(this, ShopSyncService.class);
		startService(i);
		Log.i(getClass().getName(), "Sync service started");
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			final SettingsDialog settingsDialog = new SettingsDialog(this);
			settingsDialog.show();
			break;
		default:
			break;
		}
		return true;
	}

}
