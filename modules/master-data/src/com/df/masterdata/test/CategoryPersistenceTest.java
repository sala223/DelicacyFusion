package com.df.masterdata.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.df.core.persist.testsuit.JPATestBase;
import com.df.masterdata.entity.Category;
import com.df.masterdata.service.inf.CategoryServiceInf;

@ContextConfiguration(locations = { "classpath:META-INF/master-data-beans.xml" })
public class CategoryPersistenceTest extends JPATestBase {

    @Autowired
    private CategoryServiceInf categoryService;

    @Test
    public void testNewCategory() {
	Category newCategory = new Category();
	newCategory.fillDefaultValue();
	newCategory.setName("sweet");
	newCategory.setId(123l);
	categoryService.newCategory(newCategory, null);
	List<Category> categories = categoryService.getRootCategories();
	boolean hasFound = false;
	for (Category c : categories) {
	    if (c.getName().equals(newCategory.getName())) {
		hasFound = true;
	    }
	}
	TestCase.assertTrue(hasFound);
    }

}
