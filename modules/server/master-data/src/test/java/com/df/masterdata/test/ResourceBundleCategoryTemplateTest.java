package com.df.masterdata.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.auxiliary.template.CategoryProfile;
import com.df.masterdata.auxiliary.template.CategoryTemplate;

public class ResourceBundleCategoryTemplateTest extends MasterDataJPABaseTest {

    @Autowired
    private CategoryTemplate categoryTemplate;

    @Test
    public void testGetCategories() {
	CategoryProfile[] categories = categoryTemplate.getCategories();
	TestCase.assertTrue(categories.length > 0);
    }

}
