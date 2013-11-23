package com.df.android.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.entity.Floor;
import com.df.android.entity.Store;
import com.df.android.table.TableLayoutPagerAdapter;

public class TableLayoutView extends LinearLayout {

	private RadioGroup navigator = null;
	private ViewPager pager = null;

	public TableLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater li = LayoutInflater.from(this.getContext());

		// Add navigator
		if (navigator == null) {
			navigator = (RadioGroup) li.inflate(R.layout.tablelayoutnavigator,
					this, false);
			this.addView(navigator);
		} else {
			navigator.removeAllViews();
		}

		int i = 0;
		Store store = GlobalSettings.instance().getCurrentStore();
		for (Floor floor: store.getFloors()) {
			RadioButton rb = (RadioButton) li.inflate(R.layout.tablenavigatorbutton,
					navigator, false);
			rb.setId(i++);
			rb.setText("" + floor.getFloor() + getContext().getResources().getString(getContext().getResources().getIdentifier(getContext().getPackageName() + ":string/floor", null, null)));
			navigator.addView(rb);
		}

		// Add pager
		if (pager == null) {
			pager = (ViewPager) li.inflate(R.layout.tablelayoutpager, this, false);
			TableLayoutPagerAdapter adapter = new TableLayoutPagerAdapter(
					this.getContext(), store);
			pager.setAdapter(adapter);
			this.addView(pager);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrollStateChanged(int position) {

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {

				}

				@Override
				public void onPageSelected(int position) {
					if (navigator.getCheckedRadioButtonId() != position)
						navigator.check(position);
				}

			});

			navigator
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup view,
								int position) {
							if (pager.getCurrentItem() != position)
								pager.setCurrentItem(position);
						}

					});

			navigator.check(0);
		}
	}
}
