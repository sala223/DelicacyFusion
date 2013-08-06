package com.df.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class Main extends Activity { 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.main);

        final SlidingPaneLayout spl = (SlidingPaneLayout)findViewById(R.id.sliding_pane_layout);
        ((TextView)findViewById(R.id.lstOrder)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) { 
				Log.i("Main", "Panel clicked");
				spl.smoothSlideClosed();
			} 
        	
        });
        spl.openPane();
        
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
        
        gvMenu.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                Toast.makeText(Main.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
        
//        
//        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
////        layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
//        layout.setPanelSlideListener(new PanelSlideListener() {
//
//            @Override
//            public void onPanelSlide(View panel, float slideOffset) {
//                if (slideOffset < 0.2) {
//                    if (getActionBar().isShowing()) {
//                        getActionBar().hide();
//                    }
//                } else {
//                    if (!getActionBar().isShowing()) {
//                        getActionBar().show();
//                    }
//                }
//            }
//
//            @Override
//            public void onPanelExpanded(View panel) {
//
//
//            }
//
//            @Override
//            public void onPanelCollapsed(View panel) {
//
//
//            }
//        });
////        TextView t = (TextView) findViewById(R.id.brought_by);
////        t.setMovementMethod(LinkMovementMethod.getInstance());
    }
}