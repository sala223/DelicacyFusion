package com.df.order.test;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.ItemDao;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.order.dao.OrderDao;

@Transactional
public class OrderDaoTest extends OrderBaseTest {

    @Inject
    private OrderDao orderDao;

    @Inject
    private ItemTemplateDao itplDao;

    @Inject
    private ItemDao itemDao;

    @Test
    public void testListUnavaliableItems() {
	ItemTemplate itpl = new ItemTemplate();
	itpl.setCode("testItemCode001");
	itpl.setPrice(12.44f);
	itpl.setName("testItem001");
	itpl.setCategories("");
	itplDao.insert(itpl);
	Item item = new Item(itpl, "testStore");
	itemDao.newItem(item);
	List<String> itemCodes = Arrays.asList("testItemCode001", "testItemCode002");
	List<String> unavaliableItems = itemDao.listUnavaliableItems("testStore", itemCodes);
	TestCase.assertEquals(unavaliableItems.size(), 1);
	TestCase.assertEquals(unavaliableItems.get(0), "testItemCode002");
    }
}
