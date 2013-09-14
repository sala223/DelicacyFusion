package com.df.masterdata.service.impl;

import java.io.InputStream;
import java.util.List;

import com.df.masterdata.entity.Category;

public interface ResourceBundleParser {

    List<Category> parse(InputStream in);
}
