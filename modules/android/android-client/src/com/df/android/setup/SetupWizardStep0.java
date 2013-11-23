package com.df.android.setup;

import android.content.Context;
import android.view.LayoutInflater;

import com.df.android.R;

public class SetupWizardStep0 extends SetupWizardStep {

	public SetupWizardStep0(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.setupwizard_step0, this);
	}

	@Override
	public void onStepSelected() {
	}

	@Override
	public void onStepFinished() {
		
	}
}
