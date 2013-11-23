package com.df.android.setup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.df.android.R;

public class SetupWizard extends Activity {
	private int currentPos;
	private SetupParams params = new SetupParams();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupwizard);
		
		ViewPager pager = (ViewPager) findViewById(R.id.wizard);
		final List<SetupWizardStep> steps = new ArrayList<SetupWizardStep>();
		steps.add(new SetupWizardStep0(this));
		steps.add(new SetupWizardStep1(this, params));
		steps.add(new SetupWizardStep2(this, params));
		SetupWizardAdapter adapter = new SetupWizardAdapter(this, steps);
		pager.setAdapter(adapter);
		
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int state) {
				if(state == ViewPager.SCROLL_STATE_SETTLING)
					steps.get(currentPos).onStepFinished();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if(positionOffset < 1)
					currentPos = position;
			}

			@Override
			public void onPageSelected(int position) {
				steps.get(position).onStepSelected();
			}
		});
	}
}