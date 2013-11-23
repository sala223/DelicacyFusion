package com.df.android.setup;

import java.util.Hashtable;
import java.util.Map;

public class SetupParams {
	private Map<String, Object> params = new Hashtable<String, Object>();

	public void setParam(String key, Object value) {
		params.put(key, value);
	}

	public Object getParam(String key) {
		return params.get(key);
	}
}
