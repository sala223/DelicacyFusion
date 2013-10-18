package com.df.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.entity.Shop;

public class ShopView extends FrameLayout {
	private Context cxt;
	private View shopView = null;

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
		LayoutInflater li = LayoutInflater.from(cxt);

		if (GlobalSettings.getCurrentShop() == null)
			this.addView(li.inflate(R.layout.initshopview, this, false));
		else
			refreshShopView(GlobalSettings.getCurrentShop());

		new ShopViewAdapter(cxt)
				.setOnShopChangeListener(new ShopChangeListener() {

					@Override
					public void onChange(Shop shop) {
						Log.d(getClass().getName(),
								"Received shop change message");

						refreshShopView(shop);
					}
				});

	}

	private void refreshShopView(final Shop shop) {
		Log.d(getClass().getName(), "Refreshing shop view");
		LayoutInflater li = LayoutInflater.from(cxt);
		if (shopView == null) { 
			this.removeAllViews();

			shopView = li.inflate(R.layout.shopview, this, false);

			this.addView(shopView);
		}

		((MenuView) shopView.findViewById(R.id.menuView)).setShop(shop);
		((OrderView) shopView.findViewById(R.id.orderView)).setShop(shop);
	}

}
