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


