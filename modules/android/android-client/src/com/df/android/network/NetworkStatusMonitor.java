package com.df.android.network;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStatusMonitor extends BroadcastReceiver {
	List<NetworkStatusChangeListener> listeners = new ArrayList<NetworkStatusChangeListener>();
	
	public void registerListener(NetworkStatusChangeListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		for(NetworkStatusChangeListener listener : listeners) {
			listener.onNetworkStatusChange();
		}
	}

}
