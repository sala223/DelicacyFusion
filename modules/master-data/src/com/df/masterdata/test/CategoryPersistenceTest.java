package com.df.masterdata.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.df.core.persist.testsuit.JPATestBase;
import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.entity.Category;

public class CategoryPersistenceTest extends JPATestBase {

    @Autowired
    private CategoryDAL categoryDAL;

    @Test
    public void testNewCategory() {
	categoryDAL.find(Category.class, 123); 
    }

}
