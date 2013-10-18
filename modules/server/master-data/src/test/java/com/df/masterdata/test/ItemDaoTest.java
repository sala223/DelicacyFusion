package com.df.masterdata.test;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.ItemDao;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.ItemType;
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.exception.ItemException;

@Transactional
public class ItemDaoTest extends MasterDataJPABaseTest {

    @Inject
    private ItemDao itemDao;

    @Inject
    private ItemTemplateDao templateDao;

    @Before
    public void sampleItems() {
	ItemTemplate itemTemplate1 = new ItemTemplate();
	itemTemplate1.setCode("A0001");
	itemTemplate1.setName("Icecream");
	itemTemplate1.setCategories("Sweet");
	itemTemplate1.setItemUnit(ItemUnit.CUP);
	templateDao.newItemTemplate(itemTemplate1);
	ItemTemplate itemTemplate2 = new ItemTemplate();
	itemTemplate2.setCode("A0002");
	itemTemplate2.setName("DJ Fish");
	itemTemplate2.setItemUnit(ItemUnit.DISK);
	itemTemplate2.setCategories("XiangCai");
	templateDao.newItemTemplate(itemTemplate2);
	ItemTemplate itemTemplate3 = new ItemTemplate();
	itemTemplate3.setCode("A0003");
	itemTemplate3.setName("Candy1");
	itemTemplate3.setItemUnit(ItemUnit.DISK);
	itemTemplate3.setCategories("Sweet");
	templateDao.newItemTemplate(itemTemplate3);
	ItemTemplate itemTemplate4 = new ItemTemplate();
	itemTemplate4.setCode("A0004");
	itemTemplate4.setName("DouFu");
	itemTemplate4.setItemUnit(ItemUnit.DISK);
	itemTemplate4.setCategories("XiangCai");
	templateDao.newItemTemplate(itemTemplate4);
	ItemTemplate itemTemplate5 = new ItemTemplate();
	itemTemplate5.setCode("A0005");
	itemTemplate5.setName("chopsticks");
	itemTemplate5.setType(ItemType.GOODS);
	templateDao.newItemTemplate(itemTemplate5);
	Item item1 = new Item(itemTemplate1, "S1");
	Item item2 = new Item(itemTemplate2, "S1");
	Item item3 = new Item(itemTemplate3, "S1");
	Item item4 = new Item(itemTemplate4, "S1");
	Item item5 = new Item(itemTemplate5, "S1");
	itemDao.insert(item1);
	itemDao.insert(item2);
	itemDao.insert(item3);
	itemDao.insert(item4);
	itemDao.insert(item5);
    }

    @Test
    public void testInsertNewItem() {
	ItemTemplate tpl = new ItemTemplate();
	tpl.setCode("A000555");
	tpl.setName("Candy5");
	tpl.setItemUnit(ItemUnit.DISK);
	tpl.setCategories("Sweet");
	templateDao.newItemTemplate(tpl);
	Item item = new Item(tpl, "S1");
	itemDao.newItem(item);
    }

    @Test
    public void testInsertItemWithSameItemCode() {
	Item item = new Item(templateDao.getItemTemplateByCode("A0001"), "S1");
	try {
	    itemDao.newItem(item);
	    TestCase.fail();
	} catch (ItemException ex) {
	    TestCase.assertEquals(ex.getErrorCode(), ItemException.ITEM_WITH_CODE_ALREADY_EXIST);
	}
    }

    @Test
    public void testGetItemCountGroupByCategory() {
	Map<String, Long> results = itemDao.getItemCountGroupByCategory("S1");
	TestCase.assertEquals(results.size(), 3);
	TestCase.assertTrue(results.get("XiangCai") == 2);
	TestCase.assertTrue(results.get("Sweet") == 2);
    }

    @Test
    public void testAllFoodCount() {
	long count = itemDao.allFoodCount("S1", false);
	TestCase.assertEquals(count, 4l);
    }

    @Test
    public void testGetItemsByCategory() {
	List<Item> items = itemDao.getItemsByCategory("XiangCai", "S1");
	TestCase.assertEquals(items.size(), 2);
    }
    
    @Test
    public void testGetFoodsByCategory() {
	List<Item> items = itemDao.getFoodsByCategory("XiangCai", "S1");
	TestCase.assertEquals(items.size(), 2);
    }
}
