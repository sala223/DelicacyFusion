package com.df.android.utils;

import android.content.Context;

public class ResourceUtils {
	public static String getStringByKey(Context cxt, String key) {
		return cxt.getResources().getString(cxt.getResources().getIdentifier(cxt.getPackageName() + ":string/" + key, null, null));
	}
}
