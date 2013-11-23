package com.df.android.utils;

import android.os.AsyncTask;

public abstract class WebTask<T> extends AsyncTask<String, Void, T> {
	public WebTask() {
		super();
	}
}
