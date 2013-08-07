package com.df.masterdata.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.df.core.persist.testsuit.JPATestBase;
import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.entity.Category;

@ContextConfiguration(locations = { "classpath:META-INF/master-data-beans.xml" })
public class CategoryPersistenceTest extends JPATestBase {

    @Autowired
    private CategoryDAL categoryDAL;

    @Test
    public void testNewCategory() {
	categoryDAL.find(Category.class, 123L);
    }

}
