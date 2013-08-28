package com.df.masterdata.test;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.dal.ItemDAL;
import com.df.masterdata.dal.ItemTemplateDAL;
import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.exception.ItemException;

@Transactional
public class ItemDALTest extends MasterDataJPABaseTest {

    @Inject
    private ItemDAL itemDAL;

    @Inject
    private ItemTemplateDAL templateDAL;

    @Inject
    private CategoryDAL categoryDAL;

    @Test
    public void testInsertNewItem() {
	Category category = new Category();
	category.setName("fruit");
	categoryDAL.newCategory(category, null);
	ItemTemplate template = new ItemTemplate();
	template.setCode("A0001");
	template.setName("A0001");
	template.setDescription("Good taste");
	template.addCategories(category);
	templateDAL.newItemTemplate(template);
	Item item = new Item(template);
	item.setStoreId(23);
	itemDAL.newItem(item);
    }

    @Test
    public void testInsertItemWithSameItemCode() {
	Category category = new Category();
	category.setName("fruit");
	categoryDAL.newCategory(category, null);
	ItemTemplate template = new ItemTemplate();
	template.setCode("A0001");
	template.setName("A0001");
	template.setDescription("Good taste");
	template.addCategories(category);
	templateDAL.newItemTemplate(template);
	Item item = new Item(template);
	item.setStoreId(23);
	itemDAL.newItem(item);
	itemDAL.getEntityManager().flush();
	Item item2 = new Item(template);
	try {
	    itemDAL.newItem(item2);
	    TestCase.fail();
	} catch (ItemException ex) {
	    TestCase.assertEquals(ex.getErrorCode(), ItemException.ITEM_WITH_CODE_ALREADY_EXIST);
	}
    }

    @Test
    public void testGetItemCountGroupByCategory() {
	Category category = new Category();
	category.setName("fruit");
	categoryDAL.newCategory(category, null);
	Category category2 = new Category();
	category2.setName("fish");
	categoryDAL.newCategory(category2, null);
	ItemTemplate template = new ItemTemplate();
	template.setCode("A0001");
	template.setName("A0001");
	template.setDescription("Good taste");
	template.addCategories(category,category2);
	templateDAL.newItemTemplate(template);
	ItemTemplate template2 = new ItemTemplate();
	template2.setCode("A0002");
	template2.setName("A0002");
	template2.addCategories(category,category2);
	templateDAL.newItemTemplate(template2);
	ItemTemplate template3 = new ItemTemplate();
	template3.setCode("A0003");
	template3.setName("A0003");
	template3.addCategories(category2);
	templateDAL.newItemTemplate(template3);
	Item item = new Item(template);
	itemDAL.newItem(item);
	item.setStoreId(23);
	Item item2 = new Item(template2);
	item2.setStoreId(23);
	itemDAL.newItem(item2);
	Item item3 = new Item(template3);
	item3.setStoreId(23);
	itemDAL.newItem(item3);
	itemDAL.getEntityManager().flush();
	Map<Long, Long> results = itemDAL.getItemCountGroupByCategory(23);
	TestCase.assertEquals(results.size(), 2);
	TestCase.assertTrue(results.get(1L) == 2);
	TestCase.assertTrue(results.get(2L) == 3);
    }

    @Test
    public void testGetItemsByCategory() {
	Category category = new Category();
	category.setName("fruit");
	categoryDAL.newCategory(category, null);
	ItemTemplate template = new ItemTemplate();
	template.setCode("A0001");
	template.setName("A0001");
	template.setDescription("Good taste");
	template.addCategories(category);
	templateDAL.newItemTemplate(template);
	ItemTemplate template2 = new ItemTemplate();
	template2.setCode("A0002");
	template2.setName("A0002");
	template2.addCategories(category);
	templateDAL.newItemTemplate(template2);
	ItemTemplate template3 = new ItemTemplate();
	template3.setCode("A0003");
	template3.setName("A0003");
	template3.addCategories(category);
	templateDAL.newItemTemplate(template3);
	Item item = new Item(template);
	itemDAL.newItem(item);
	item.setStoreId(23);
	Item item2 = new Item(template2);
	item2.setStoreId(23);
	itemDAL.newItem(item2);
	Item item3 = new Item(template3);
	item3.setStoreId(23);
	itemDAL.newItem(item3);
	itemDAL.getEntityManager().flush();
	List<Item> items = itemDAL.getItemsByCategory(category.getId(), 23l);
	TestCase.assertEquals(items.size(), 3);

    }
}
