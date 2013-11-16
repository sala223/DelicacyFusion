package com.df.masterdata.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.auxiliary.Category;
import com.df.masterdata.auxiliary.CategoryLoader;

public class ResourceBundleCategoryLoaderTest extends MasterDataJPABaseTest {

	@Autowired
	private CategoryLoader categoryLoader;

	@Test
	public void testGetCategories() {
		Category[] categories = categoryLoader.loadCategories();
		TestCase.assertTrue(categories.length > 0);
	}

}
