package com.df.android.setup;

import java.util.List;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class SetupWizardAdapter extends PagerAdapter {
	Context context;
	List<SetupWizardStep> steps;

	public SetupWizardAdapter(Context context, List<SetupWizardStep> steps) {
		this.context = context;
		this.steps = steps;
	}

	@Override
	public int getCount() {
		return steps.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View step = steps.get(position);
		if (container.findViewById(step.getId()) == null) 
			container.addView(step);
		
		return step;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);

	}
}
