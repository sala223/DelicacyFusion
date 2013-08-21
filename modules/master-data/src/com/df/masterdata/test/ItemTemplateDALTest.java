package com.df.masterdata.test;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import junit.framework.TestCase;

import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.dal.ItemTemplateDAL;
import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.exception.ItemTemplateException;

@Transactional
public class ItemTemplateDALTest extends MasterDataJPABaseTest {

    @Inject
    private ItemTemplateDAL itemTemplateDAL;

    @Inject
    private CategoryDAL categoryDAL;

    @Test
    public void testNewItemTemplate() {
	ItemTemplate itemTemplate = new ItemTemplate();
	itemTemplate.setCode("A0001");
	itemTemplate.setName("Icecream");
	itemTemplate.setItemUnit(ItemUnit.DISK);
	itemTemplateDAL.newItemTemplate(itemTemplate);
    }

    @Test
    public void testNewItemTemplateWithSameCodeAndSameName() {
	String code = "A0001";
	String name = "Icecream";
	ItemTemplate itemTemplate = new ItemTemplate();
	itemTemplate.setCode(code);
	itemTemplate.setName(name);
	itemTemplate.setItemUnit(ItemUnit.DISK);
	itemTemplateDAL.newItemTemplate(itemTemplate);
	itemTemplate = new ItemTemplate();
	itemTemplate.setCode(code);
	itemTemplate.setName("Icecream2");
	try {
	    itemTemplateDAL.newItemTemplate(itemTemplate);
	    TestCase.fail();
	} catch (ItemTemplateException ex) {
	    TestCase.assertEquals(ex.getErrorCode(), ItemTemplateException.ITEM_TPL_WITH_CODE_EXIST);
	}
	itemTemplate.setCode("A0002");
	itemTemplate.setName(name);
	try {
	    itemTemplateDAL.newItemTemplate(itemTemplate);
	    TestCase.fail();
	} catch (ItemTemplateException ex) {
	    TestCase.assertEquals(ex.getErrorCode(), ItemTemplateException.ITEM_TPL_WITH_NAME_EXIST);
	}
    }

    @Test
    public void testGetItemTemplateByCode() {
	ItemTemplate itemTemplate = new ItemTemplate();
	String code = "A0001";
	String name = "Icecream";
	itemTemplate.setCode(code);
	itemTemplate.setName(name);
	itemTemplate.setItemUnit(ItemUnit.DISK);
	itemTemplateDAL.newItemTemplate(itemTemplate);
	itemTemplate = itemTemplateDAL.getItemTemplateByCode(code);
	TestCase.assertNotNull(itemTemplate);
    }

    @Test
    public void testGetItemTemplateByName() {
	ItemTemplate itemTemplate = new ItemTemplate();
	String code = "A0001";
	String name = "Icecream";
	itemTemplate.setCode(code);
	itemTemplate.setName(name);
	itemTemplate.setItemUnit(ItemUnit.DISK);
	itemTemplateDAL.newItemTemplate(itemTemplate);
	itemTemplate = itemTemplateDAL.getItemTemplateByName(name);
	TestCase.assertNotNull(itemTemplate);
    }

    @Test
    public void testAddItemTemplateWithCategory() {
	Category c = new Category();
	c.setName("Juice");
	Category c2 = new Category();
	c2.setName("Sweet");
	categoryDAL.newCategory(c, null);
	categoryDAL.newCategory(c2, null);
	ItemTemplate itemTemplate = new ItemTemplate();
	String code = "A0001";
	String name = "Icecream";
	itemTemplate.setCode(code);
	itemTemplate.setName(name);
	itemTemplate.setItemUnit(ItemUnit.DISK);
	itemTemplate.setCategories(c, c2);
	itemTemplateDAL.newItemTemplate(itemTemplate);
	itemTemplate = itemTemplateDAL.getItemTemplateByCode(code);
	TestCase.assertNotNull(itemTemplate);
	TestCase.assertEquals(itemTemplate.getCategories().size(), 2);
    }
}
