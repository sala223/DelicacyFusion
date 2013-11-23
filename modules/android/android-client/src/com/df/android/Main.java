package com.df.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.df.android.menu.CategoryDictionary;
import com.df.android.network.NetworkStatusChangeListener;
import com.df.android.network.NetworkStatusMonitor;
import com.df.android.ui.SettingsDialog;
import com.df.android.ui.TableLayoutDlg;
import com.google.gson.Gson;

public class Main extends Activity implements NetworkStatusChangeListener {
	NetworkStatusMonitor networkMonitor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();
//		startServices();
		initView();
	}

	@Override
	protected void onStop() {
		super.onStop();
		this.unregisterReceiver(networkMonitor);
	}
	
	private void init() {
		Log.d(getClass().getName(), "Initialize category dictionary");
		
		try {
			Gson json = new Gson();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							new File(getExternalCacheDir() + "/catdic.json"))));

			CategoryDictionary catDic = json.fromJson(reader, CategoryDictionary.class);

			GlobalSettings.instance().setCategoryDictionary(catDic);
			
			Log.d(getClass().getName(), "Category dictionary initialized");
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to initialize dictionary initialized due to " + e);
		}
	}

//	private void startServices() {
//		startService(new Intent(this, StoreSyncService.class));
//	}
	
	private void initView() {
		setContentView(R.layout.main);
		
		networkMonitor = new NetworkStatusMonitor();
		networkMonitor.registerListener(this);
		registerReceiver(networkMonitor, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
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
		case R.id.menu_tablelayout:
			final TableLayoutDlg dlg = new TableLayoutDlg(this);
			dlg.show();
			break;
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
