package com.df.android.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuEventDispatcher {
	private static List<ItemOrderListener> itemOrderListeners = new ArrayList<ItemOrderListener>();
	
	public static void registerItemOrderListener(ItemOrderListener listener) {
		itemOrderListeners.add(listener);
	}
	
	public static void sendEvent(ItemOrderEvent event) {
		for(ItemOrderListener listener : itemOrderListeners) {
			listener.onItemOrdered(event.getItem());
		}
	}
}
