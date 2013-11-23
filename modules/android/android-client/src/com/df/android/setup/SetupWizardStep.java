package com.df.android.setup;

import android.content.Context;
import android.widget.RelativeLayout;

public abstract class SetupWizardStep extends RelativeLayout implements SetupWizardStepChangeListener {
	public SetupWizardStep(Context context) {
		super(context);
	}

}
