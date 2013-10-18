package com.df.android.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.df.android.GlobalSettings;
import com.df.android.R;

public class SettingsDialog extends Dialog {
	SharedPreferences preferences;
	
	public SettingsDialog(final Context cxt) {
		super(cxt);
		setContentView(R.layout.settings);
		setTitle(R.string.settings);
		
		init(cxt);
	}

	private void init(final Context cxt) {
		final EditText etServerAddr = (EditText)findViewById(R.id.etServerAddr);
		preferences = PreferenceManager.getDefaultSharedPreferences(cxt); 
		etServerAddr.setText(preferences.getString("SERVERURL", "http://localhost"));
		
		((Button) findViewById(R.id.btnConfirmSettings))
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				applySettings();
				dismiss();
			}
		});
	}
	
	private void applySettings() {
		Editor edit = preferences.edit();
		
		String serverUrl = ((EditText)findViewById(R.id.etServerAddr)).getText().toString();
		edit.putString("SERVERURL", serverUrl);
		edit.apply();
		
		GlobalSettings.setServerUrl(serverUrl);
	}
}
