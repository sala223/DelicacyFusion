package com.df.android.setup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.df.android.GlobalSettings;
import com.df.android.Main;
import com.df.android.R;
import com.df.android.entity.Store;
import com.df.android.entity.Tenant;

public class SetupWizardStep2 extends SetupWizardStep {
	private final SetupParams params;
	private StoreListAdapter storeListAdapter;

	public SetupWizardStep2(Context context) {
		this(context, null);
	}

	public SetupWizardStep2(Context context, final SetupParams params) {
		super(context);
		this.params = params;

		LayoutInflater.from(context).inflate(R.layout.setupwizard_step2, this);

		storeListAdapter = new StoreListAdapter(context);
		ListView storeList = (ListView) findViewById(R.id.storeName);
		storeList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				params.setParam("STORE", storeListAdapter.getItem(position));
			}
			
		});
		
		storeList.setAdapter(storeListAdapter);

		((Button) findViewById(R.id.btnFinish))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						onSetupComplete();
					}

				});
	}

	@Override
	public void onStepSelected() {
		Tenant tenant = (Tenant) params.getParam("TENANT");

		if (tenant != null) {
			// TODO: Get stores of the tenant
			Store[] stores = { new Store("001", "abc"), new Store("002", "def") };

			storeListAdapter.setStores(stores);
			storeListAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onStepFinished() {
	}

	private void onSetupComplete() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this.getContext());
		Editor edit = preferences.edit();

		String serverUrl = (String) params.getParam("SERVER_ADDR");
		edit.putString("SERVERURL", serverUrl);

		Tenant tenant = (Tenant) params.getParam("TENANT");
		if (tenant != null) {
			edit.putString("TENANT_CODE", tenant.getCode());
			edit.putString("TENANT_NAME", tenant.getName());

			Store store = (Store) params.getParam("STORE");
			if (store != null) {
				edit.putString("STORE_CODE", store.getCode());
				edit.putString("STORE_NAME", store.getName());
			}
		}

		edit.apply();

		GlobalSettings.instance().setServerUrl(serverUrl);

		this.getContext().startActivity(
				new Intent(this.getContext(), Main.class));
	}

	private class StoreListAdapter extends BaseAdapter {
		private final Context context;
		private Store[] stores = null;

		public StoreListAdapter(Context context) {
			super();
			this.context = context;
		}

		public void setStores(Store[] stores) {
			this.stores = stores;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if(rowView == null)
				rowView = LayoutInflater.from(context).inflate(
					R.layout.storelistitem, parent, false);

			((TextView) rowView.findViewById(R.id.storeCode)).setText(stores[position].getCode());
			((TextView) rowView.findViewById(R.id.storeName)).setText(stores[position].getName());

			return rowView;
		}

		@Override
		public int getCount() {
			return stores != null ? stores.length : 0;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return stores != null ? stores[position] : null;
		}
	}
}
