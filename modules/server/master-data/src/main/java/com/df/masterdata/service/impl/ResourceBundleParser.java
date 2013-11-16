package com.df.masterdata.service.impl;

import java.io.InputStream;
import java.util.Map;

import com.df.masterdata.auxiliary.Category;

public interface ResourceBundleParser {

	Map<String, Category> parse(InputStream in);
}
