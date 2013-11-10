package com.df.order.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.ItemDao;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.ItemType;
import com.df.masterdata.entity.ItemUnit;
import com.df.order.entity.Order;
import com.df.order.entity.OrderLine;
import com.df.order.service.contract.OrderService;

@Transactional
public class OrderServiceTest extends OrderBaseTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemTemplateDao templateDao;

    @Before
    public void sampleItems() {
	ItemTemplate itemTemplate1 = new ItemTemplate();
	itemTemplate1.setCode("A0001");
	itemTemplate1.setName("Icecream");
	itemTemplate1.setCategories("Sweet");
	itemTemplate1.setItemUnit(ItemUnit.CUP);
	itemTemplate1.setPrice(10);
	templateDao.newItemTemplate(itemTemplate1);
	ItemTemplate itemTemplate2 = new ItemTemplate();
	itemTemplate2.setCode("A0002");
	itemTemplate2.setName("DJ Fish");
	itemTemplate2.setItemUnit(ItemUnit.DISK);
	itemTemplate2.setCategories("XiangCai");
	itemTemplate2.setPrice(10);
	templateDao.newItemTemplate(itemTemplate2);
	ItemTemplate itemTemplate3 = new ItemTemplate();
	itemTemplate3.setCode("A0003");
	itemTemplate3.setName("Candy1");
	itemTemplate3.setItemUnit(ItemUnit.DISK);
	itemTemplate3.setCategories("Sweet");
	itemTemplate3.setPrice(10);
	templateDao.newItemTemplate(itemTemplate3);
	ItemTemplate itemTemplate4 = new ItemTemplate();
	itemTemplate4.setCode("A0004");
	itemTemplate4.setName("DouFu");
	itemTemplate4.setItemUnit(ItemUnit.DISK);
	itemTemplate4.setCategories("XiangCai");
	itemTemplate4.setPrice(10);
	templateDao.newItemTemplate(itemTemplate4);
	ItemTemplate itemTemplate5 = new ItemTemplate();
	itemTemplate5.setCode("A0005");
	itemTemplate5.setName("chopsticks");
	itemTemplate5.setType(ItemType.GOODS);
	itemTemplate5.setPrice(10);
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
    public void testCreateOrder() {
	Order order = new Order();
	order.setStoreCode("S1");
	OrderLine line = new OrderLine("A0001", 12, 12);
	order.addOrderLine(line);
	orderService.createOrder(0, order);
    }
}
