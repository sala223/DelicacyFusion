package com.df.masterdata.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.masterdata.entity.RuleDefinition;
import com.df.masterdata.entity.RuleParameter;

@Transactional
public class PromotionDaoTest extends MasterDataJPABaseTest {

    @Autowired
    private PromotionDao promotionDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemTemplateDao templateDao;

    @Before
    public void sampleItemsAndPromotions() {
	ItemTemplate template1 = new ItemTemplate();
	template1.setCode("A0001");
	template1.setName("Icecream");
	template1.setCategories("Sweet", "Cold");
	template1.setItemUnit(ItemUnit.CUP);
	templateDao.newItemTemplate(template1);
	ItemTemplate template2 = new ItemTemplate();
	template2.setCode("A0002");
	template2.setName("Candy");
	template2.setCategories("Sweet");
	template2.setItemUnit(ItemUnit.CUP);
	templateDao.newItemTemplate(template2);
	Item item1 = new Item(template1, "S1");
	Item item2 = new Item(template2, "S1");
	itemDao.insert(item1);
	itemDao.insert(item2);
	Promotion p1 = new Promotion();
	p1.setName("30% discount");
	p1.setItems(item1.getCode());
	p1.setType(PromotionType.ITEM);
	p1.setDetails("Discount 30%");
	p1.setStoreCode("S1");
	RuleDefinition rule = new RuleDefinition("testRule");
	rule.addParameter(new RuleParameter("discount", "0.3"));
	p1.setRule(rule);
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.DAY_OF_YEAR, 10);
	p1.setValidTo(calendar.getTime());
	promotionDao.insert(p1);
	Promotion p2 = new Promotion();
	p2.setName("Sweet 30% discount");
	p2.setCategories("Sweet", "fakeCategory");
	p2.setType(PromotionType.CATEGORY);
	p2.setDetails("Discount 30%");
	p2.setStoreCode("S1");
	calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.DAY_OF_YEAR, 10);
	p2.setValidTo(calendar.getTime());
	RuleDefinition rule2 = new RuleDefinition("testRule");
	rule2.addParameter(new RuleParameter("discount", "0.3"));
	p2.setRule(rule2);
	promotionDao.insert(p2);
	Promotion p3 = new Promotion();
	p3.setName("20% discount");
	p3.setItems(item2.getCode());
	p3.setDetails("Discount 20%");
	p3.setStoreCode("S1");
	p3.setType(PromotionType.ITEM);
	calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.DAY_OF_YEAR, 10);
	p3.setValidTo(calendar.getTime());
	RuleDefinition rule3 = new RuleDefinition("testRule");
	rule3.addParameter(new RuleParameter("discount", "0.2"));
	p3.setRule(rule3);
	promotionDao.insert(p3);
    }

    @Test
    public void getValidItemsPromotions() {
	List<Promotion> promotions = promotionDao.getValidItemsPromotions("S1");
	TestCase.assertEquals(3, promotions.size());
    }

    @Test
    public void getValidItemPromotions() {
	Item item = itemDao.getItemByItemCode("S1", "A0001");
	List<Promotion> promotions = promotionDao.getValidItemPromotions(item);
	TestCase.assertEquals(2, promotions.size());
    }
    
    @Test
    public void getValidPromotionsByType() {
 	List<Promotion> promotions = promotionDao.getValidPromotionsByType("S1", PromotionType.ITEM);
	TestCase.assertEquals(2, promotions.size());
    }
}
