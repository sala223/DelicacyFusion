package com.df.order.test;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.ItemDao;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.dao.PromotionDao;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.ItemType;
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.RuleDefinition;
import com.df.masterdata.entity.RuleParameter;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.masterdata.promotion.rule.ItemDiscountRule;
import com.df.order.entity.Order;
import com.df.order.entity.OrderLine;
import com.df.order.entity.ServiceCard;
import com.df.order.service.contract.OrderService;
import com.df.order.service.contract.ServiceCardService;

@Transactional
public class OrderServiceTest extends OrderBaseTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ServiceCardService serviceCardService;

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private ItemTemplateDao templateDao;

	@Autowired
	private PromotionDao promotionDao;

	@Before
	public void sampleItems() {
		ItemTemplate itemTemplate1 = new ItemTemplate();
		itemTemplate1.setCode("TESTA0001");
		itemTemplate1.setName("TESTIcecream");
		itemTemplate1.setCategories("Sweet");
		itemTemplate1.setItemUnit(ItemUnit.CUP);
		itemTemplate1.setPrice(10);
		templateDao.newItemTemplate(itemTemplate1);
		ItemTemplate itemTemplate2 = new ItemTemplate();
		itemTemplate2.setCode("TESTA0002");
		itemTemplate2.setName("DJ Fish");
		itemTemplate2.setItemUnit(ItemUnit.DISK);
		itemTemplate2.setCategories("XiangCai");
		itemTemplate2.setPrice(10);
		templateDao.newItemTemplate(itemTemplate2);
		ItemTemplate itemTemplate3 = new ItemTemplate();
		itemTemplate3.setCode("TESTA0003");
		itemTemplate3.setName("Candy1");
		itemTemplate3.setItemUnit(ItemUnit.DISK);
		itemTemplate3.setCategories("Sweet");
		itemTemplate3.setPrice(10);
		templateDao.newItemTemplate(itemTemplate3);
		ItemTemplate itemTemplate4 = new ItemTemplate();
		itemTemplate4.setCode("TESTA0004");
		itemTemplate4.setName("DouFu");
		itemTemplate4.setItemUnit(ItemUnit.DISK);
		itemTemplate4.setCategories("XiangCai");
		itemTemplate4.setPrice(10);
		templateDao.newItemTemplate(itemTemplate4);
		ItemTemplate itemTemplate5 = new ItemTemplate();
		itemTemplate5.setCode("TESTA0005");
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
		OrderLine line = new OrderLine("TESTA0001", 12);
		order.addOrderLine(line);
		orderService.createOrder("S1", 0, order);
	}

	@Test
	public void testCreateOrderWithServiceCard() {
		ServiceCard card = serviceCardService.createServiceCard("S1", "S1000001");
		itemDao.getEntityManager().flush();
		Order order = new Order();
		order.setStoreCode("S1");
		OrderLine line = new OrderLine("TESTA0001", 12);
		order.addOrderLine(line);
		order.setServiceCardId(card.getId());
		orderService.createOrder("S1", 0, order);
	}

	@Test
	public void testGetOrderByTable() {
		ServiceCard card = serviceCardService.createServiceCard("S1", "S1000001");
		itemDao.getEntityManager().flush();
		Order order = new Order();
		order.setStoreCode("S1");
		OrderLine line = new OrderLine("TESTA0001", 12);
		order.addOrderLine(line);
		order.setServiceCardId(card.getId());
		orderService.createOrder("S1", 0, order);
		itemDao.getEntityManager().flush();
		Order found = orderService.getOrderByTable("S1", "S1000001");
		TestCase.assertNotNull(found);
	}

	@Test
	public void testCreateOrderWithPromotionAndServiceCard() {
		Promotion promotion = new Promotion();
		promotion.setCode("NAT_5_DIS");
		promotion.setName("National day 50% discount");
		promotion.setItems("TESTA0001");
		promotion.setDetails("Food TESTA0001 with 50% discount in national day.");
		promotion.setType(PromotionType.ITEM);
		promotion.setStoreCode("S1");
		RuleDefinition rule = new RuleDefinition("itemDiscount");
		rule.addParameter(new RuleParameter(ItemDiscountRule.DISCOUNT_PAR, "5"));
		promotion.setRule(rule);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 10);
		promotion.setValidTo(calendar.getTime());
		promotionDao.insert(promotion);
		ServiceCard card = serviceCardService.createServiceCard("S1", "S1000001");
		itemDao.getEntityManager().flush();
		Order order = new Order();
		order.setStoreCode("S1");
		OrderLine line = new OrderLine("TESTA0001", 2);
		OrderLine line2 = new OrderLine("TESTA0002", 2);
		order.addOrderLine(line);
		order.addOrderLine(line2);
		order.setServiceCardId(card.getId());
		orderService.createOrder("S1", 0, order);
		itemDao.getEntityManager().flush();
		Order found = orderService.getOrderByTable("S1", "S1000001");
		TestCase.assertNotNull(found);
	}
}
