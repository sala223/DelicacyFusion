package com.df.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ScrollView;

public class Main extends Activity { 
	
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);

        GridView gvMenu = (GridView) findViewById(R.id.gvMenu); 
        List<MenuItem> menus = new ArrayList<MenuItem>();  
		try { 
	        String imageFiles[] = this.getAssets().list("cache/image");
	        for (int i = 0; i < imageFiles.length; ++i) {
	        	Log.i("Main", "found " + imageFiles[i]);
	            menus.add(new MenuItem("清蒸鲈鱼", "cache/image/" + imageFiles[i]));
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        gvMenu.setAdapter(new MenuAdapter(this, menus));
        
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