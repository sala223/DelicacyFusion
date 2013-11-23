package com.df.android.setup;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.df.android.R;
import com.df.android.entity.Tenant;

public class SetupWizardStep1 extends SetupWizardStep {
	private SetupParams params;
	
	public SetupWizardStep1(Context context) {
		this(context, null);
	}

	public SetupWizardStep1(Context context, SetupParams params) {
		super(context);
		this.params = params;
		LayoutInflater.from(context).inflate(R.layout.setupwizard_step1, this);
	}

	@Override
	public void onStepSelected() {
	}

	@Override
	public void onStepFinished() {
		params.setParam("SERVER_ADDR", ((EditText)findViewById(R.id.serverAddr)).getText().toString());
		String tenantCode = ((EditText)findViewById(R.id.tenantCode)).getText().toString();
		// Get tenant from code
		Tenant tenant = new Tenant(tenantCode, tenantCode);
		params.setParam("TENANT", tenant);
	}
}
