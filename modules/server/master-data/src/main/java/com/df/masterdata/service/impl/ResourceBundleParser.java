package com.df.masterdata.service.impl;

import java.io.InputStream;
import java.util.Map;

import com.df.masterdata.auxiliary.template.CategoryProfile;

public interface ResourceBundleParser {

    Map<String, CategoryProfile> parse(InputStream in);
}
