package com.df.android.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Store;
import com.df.android.menu.MenuPagerAdapter;

public class MenuView extends LinearLayout {
	private RadioGroup menuNavigator = null;
	private ViewPager menuPager = null;

	public MenuView(Context context) {
		super(context);
		initView();
	}

	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		Log.d(getClass().getName(), "Refreshing shop menu");

		LayoutInflater li = LayoutInflater.from(this.getContext());

		// Add menu navigator
		if (menuNavigator == null) {
			menuNavigator = (RadioGroup) li.inflate(R.layout.menunavigator,
					this, false);
			this.addView(menuNavigator);
		} else {
			menuNavigator.removeAllViews();
		}

		int i = 0;
		Store store = GlobalSettings.instance().getCurrentStore();
		for (ItemCategory category : store.getNavigatableMenuItemCategories()) {
			RadioButton rb = (RadioButton) li.inflate(
					R.layout.menunavigatorbutton, menuNavigator, false);
			rb.setId(i++);
			rb.setText(category.getName());
			menuNavigator.addView(rb);
		}

		// Add menu pager
		if (menuPager == null) {
			menuPager = (ViewPager) li.inflate(R.layout.menupager, this, false);
			MenuPagerAdapter adpater = new MenuPagerAdapter(this.getContext(),
					store);
			menuPager.setAdapter(adpater);
			this.addView(menuPager);
			menuPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrollStateChanged(int position) {

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {

				}

				@Override
				public void onPageSelected(int position) {
					if (menuNavigator.getCheckedRadioButtonId() != position)
						menuNavigator.check(position);
				}

			});

			menuNavigator
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup view,
								int position) {
							if (menuPager.getCurrentItem() != position)
								menuPager.setCurrentItem(position);
						}

					});

			menuNavigator.check(0);
		}
	}
}
