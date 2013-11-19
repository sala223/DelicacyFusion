package com.df.masterdata.auxiliary;

import java.io.InputStream;
import java.util.Map;

public interface ResourceBundleParser {

	Map<String, Category> parse(InputStream in);
}
