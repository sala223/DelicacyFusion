package com.df.android.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.df.android.R;

public class TableLayoutDlg extends Dialog {

	public TableLayoutDlg(final Context cxt) {
		super(cxt);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tablelayout);
	}
}
