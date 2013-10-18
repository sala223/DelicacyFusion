package com.df.masterdata.test;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import junit.framework.TestCase;

import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.exception.ItemTemplateException;

@Transactional
public class ItemTemplateDaoTest extends MasterDataJPABaseTest {

    @Inject
    private ItemTemplateDao itemTemplateDAL;

    @Before
    public void sampleItemTemplates() {
	ItemTemplate itemTemplate = new ItemTemplate();
	itemTemplate.setCode("A0001");
	itemTemplate.setName("Icecream");
	itemTemplate.setItemUnit(ItemUnit.DISK);
	itemTemplateDAL.newItemTemplate(itemTemplate);
    }

    @Test
    public void testNewItemTemplate() {
	ItemTemplate itemTemplate = new ItemTemplate();
	itemTemplate.setCode("newItem");
	itemTemplate.setName("newItemName");
	itemTemplate.setItemUnit(ItemUnit.CUP);
	itemTemplateDAL.newItemTemplate(itemTemplate);
    }

    @Test
    public void testNewItemTemplateWithSameCodeOrSameName() {
	String code = "A0001";
	String name = "Icecream";
	ItemTemplate itemTemplate = new ItemTemplate();
	itemTemplate.setCode(code);
	itemTemplate.setName(name + "added");
	try {
	    itemTemplateDAL.newItemTemplate(itemTemplate);
	    TestCase.fail();
	} catch (ItemTemplateException ex) {
	    TestCase.assertEquals(ex.getErrorCode(), ItemTemplateException.ITEM_TPL_WITH_CODE_EXIST);
	}
	itemTemplate = new ItemTemplate();
	itemTemplate.setCode(code + "added");
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
	ItemTemplate itemTemplate = itemTemplateDAL.getItemTemplateByCode("A0001");
	TestCase.assertNotNull(itemTemplate);
    }

    @Test
    public void testGetItemTemplateByName() {
	ItemTemplate itemTemplate = itemTemplateDAL.getItemTemplateByName("Icecream");
	TestCase.assertNotNull(itemTemplate);
    }

    @Test
    public void testAddItemTemplateWithCategory() {
	ItemTemplate itemTemplate = new ItemTemplate();
	String code = "newItem";
	String name = "newItemName";
	itemTemplate.setCode(code);
	itemTemplate.setName(name);
	itemTemplate.setItemUnit(ItemUnit.DISK);
	itemTemplate.setCategories("Sweet", "Code");
	itemTemplateDAL.newItemTemplate(itemTemplate);
	itemTemplate = itemTemplateDAL.getItemTemplateByCode(code);
	TestCase.assertNotNull(itemTemplate);
	TestCase.assertEquals(itemTemplate.getCategories().size(), 2);
    }
}
