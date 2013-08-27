package com.df.masterdata.test;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.entity.Category;
import com.df.masterdata.exception.CategoryException;

@Transactional
public class CategoryDALTest extends MasterDataJPABaseTest {

    @Inject
    private CategoryDAL categoryDAL;

    @Test
    public void testCreateFindAndRemove() {
	String name = "juice";
	Category category = new Category();
	category.setName(name);
	categoryDAL.insert(category);
	Category found = categoryDAL.findCategoryByName(name);
	TestCase.assertNotNull(found);
	categoryDAL.removeCategoryById(found.getId());
	found = categoryDAL.findCategoryByName(name);
	TestCase.assertNull(found);
    }

    @Test
    public void testCreateWithDuplicateName() {
	String name = "juice";
	Category category = new Category();
	category.setName(name);
	categoryDAL.insert(category);
	category = new Category();
	category.setName(name);
	try {
	    categoryDAL.newCategory(category, null);
	    TestCase.fail();
	} catch (CategoryException ex) {
	    TestCase.assertEquals(ex.getErrorCode(), CategoryException.CATEGORY_WITH_NAME_EXIST);
	}
    }

    @Test
    public void testCreateWithParent() {
	String parentName = "parent";
	Category parent = new Category();
	parent.setName(parentName);
	categoryDAL.insert(parent);
	Category category = new Category();
	String name = "juice";
	category.setName(name);
	categoryDAL.getEntityManager().flush();
	categoryDAL.newCategory(category, parent.getId());
	Category found = categoryDAL.findCategoryByName(name);
	TestCase.assertNotNull(found.getParent());
	TestCase.assertEquals(found.getParent().getName(), parentName);
    }
}
