package com.df.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.entity.Store;

public class ShopView extends FrameLayout {
	private Context cxt;
	private boolean initialized = false;

	public ShopView(Context context) {
		super(context);
		this.cxt = context;
		initView();
	}

	public ShopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.cxt = context;
		initView();
	}

	private void initView() {
		Log.d(getClass().getName(), "Init shop view");
		ShopViewAdapter adapter = new ShopViewAdapter(cxt);

//		Store store = GlobalSettings.instance().getCurrentStore(); 
//		if (store == null || store.getMenu() == null) {
//			initialized = false;
//			LayoutInflater.from(cxt).inflate(R.layout.initshopview, this);
//		} 
//		else
			refreshShopView(GlobalSettings.instance().getCurrentStore());

		adapter.setOnShopChangeListener(new ShopChangeListener() {
			@Override
			public void onChange(Store store) {
				refreshShopView(store);
			}
		});

	}

	private void refreshShopView(final Store store) {
		Log.d(getClass().getName(), "Refreshing shop view");
		if (!initialized) {
			this.removeAllViews();
			LayoutInflater.from(cxt).inflate(R.layout.shopview, this);
			initialized = true;
		}
	}

}
