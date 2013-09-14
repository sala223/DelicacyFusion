package com.df.masterdata.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.df.masterdata.entity.Category;
import com.df.masterdata.service.contract.CategoryServiceInf;

public class ResourceBundleCategoryServiceTest extends MasterDataJPABaseTest {

    @Autowired
    @Qualifier("resourceBundleCategoryService")
    private CategoryServiceInf categoryService;

    @Test
    public void testGetCategories() {
	List<Category> categories = categoryService.getCategories();
	TestCase.assertTrue(categories.size() > 0);
    }

}
