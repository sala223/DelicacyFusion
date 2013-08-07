package com.df.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;

public class Main extends Activity { 
	
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);

        Order order = new Order();
        GridView gvMenu = (GridView) findViewById(R.id.gvMenu); 
        List<MenuItem> menus = new ArrayList<MenuItem>();  
		try { 
	        String imageFiles[] = this.getAssets().list("cache/image");
	        for (int i = 0; i < imageFiles.length; ++i) {
	        	MenuItem item = new MenuItem("清蒸鲈鱼", MenuItem.MenuItemType.MIT_HOTDISH, "cache/image/" + imageFiles[i]); 
	            menus.add(item);
	            order.add(item);
	        }
		} catch (IOException e) {
			Log.e(getClass().getName(), "Fail to load menus due to " + e);
		}
        gvMenu.setAdapter(new MenuAdapter(this, menus));
        
        ListView lstOrder = (ListView)findViewById(R.id.lstOrder);
        OrderAdapter orderAdapter = new OrderAdapter();
        orderAdapter.setOrder(order);
        lstOrder.setAdapter(orderAdapter);
        
        findViewById(R.id.openClose).setOnClickListener(new OnClickListener()
        {  
			@Override
			public void onClick(View arg0) {
            	ScrollView leftPane = (ScrollView)findViewById(R.id.leftPane);
            	
				if(leftPane.getVisibility() == View.VISIBLE) {
					leftPane.setVisibility(View.GONE);
				}else {
					leftPane.setVisibility(View.VISIBLE);
				}
			}
        });
    }
    
}

//package com.df.android;
//
//import android.database.DataSetObserver;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListAdapter;
//
//public class OrderAdapter implements ListAdapter {
//	Order order;
//	
//	public void setOrder(Order order) {
//		this.order = order;
//	}
//	
//	@Override
//	public int getCount() {
//		return 0;
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getItemViewType(int arg0) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public View getView(int arg0, View arg1, ViewGroup arg2) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getViewTypeCount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isEmpty() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void registerDataSetObserver(DataSetObserver arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void unregisterDataSetObserver(DataSetObserver arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean areAllItemsEnabled() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isEnabled(int arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//}
//
//
//package com.df.android;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Order {
//    private class MenuItemOrder {
//    	private MenuItem item;
//    	public MenuItem getItem() {
//			return item;
//		}
//
//		public int getCopies() {
//			return copies;
//		}
//
//		public String getComments() {
//			return comments;
//		}
//
//		private int copies = 1;
//    	private String comments = "";
//    	
//    	public MenuItemOrder(MenuItem item) {
//    		this.item = item;
//    	}
//    }
//	
//	private String id;
//	private List<MenuItemOrder> items[] = new ArrayList[MenuItem.dishTypes.length];
//	private Order next;
//	
//	public Order() {
//		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
//			items[i] = new ArrayList<MenuItemOrder>();
//	}
//	
//	public void add(MenuItem item) {
//		if(item == null) 
//			return;
//		
//		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
//			if(MenuItem.dishTypes[i] == item.getType())
//				items[i].add(new MenuItemOrder(item));
//	}
//}
