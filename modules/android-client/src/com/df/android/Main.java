package com.df.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.df.android.entity.Shop;
import com.df.android.network.NetworkStatusChangeListener;
import com.df.android.network.NetworkStatusMonitor;
import com.df.android.service.ShopSyncService;
import com.df.android.ui.SettingsDialog;

public class Main extends Activity implements NetworkStatusChangeListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		NetworkStatusMonitor networkMonitor = new NetworkStatusMonitor();
		networkMonitor.registerListener(this);
		registerReceiver(networkMonitor, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));

		GlobalSettings.setCurrentShop(new Shop("demo"));

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

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm != null) {
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			return netInfo != null && netInfo.isConnectedOrConnecting();
		}

		return false;
	}

	@Override
	public void onNetworkStatusChange() {
		TextView tvNetworkStatus = (TextView) findViewById(R.id.tvNetworkStatus);
		if (isOnline()) {
			tvNetworkStatus.setVisibility(View.GONE);
		} else {
			tvNetworkStatus.setText(R.string.network_unavailable);
			tvNetworkStatus.setVisibility(View.VISIBLE);
		}
	}
}
