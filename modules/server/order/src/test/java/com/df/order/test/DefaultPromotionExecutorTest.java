package com.df.order.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.RuleDefinition;
import com.df.masterdata.entity.RuleParameter;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.order.promotion.DefaultPromotionExecutor;
import com.df.order.promotion.PromotionExecutor;
import com.df.order.promotion.rule.DefaultRuleRepository;
import com.df.order.promotion.rule.ItemDiscountRule;
import com.df.order.promotion.rule.TastePriceRule;

public class DefaultPromotionExecutorTest {

    private static PromotionExecutor promotionExecutor;

    static {
	promotionExecutor = new DefaultPromotionExecutor(new DefaultRuleRepository());
    }

    @Test
    public void testTastePriceRule() {
	ItemTemplate tpl = new ItemTemplate();
	tpl.setCode("A0001");
	tpl.setName("Icecream");
	tpl.setCategories("Sweet", "Cold");
	tpl.setItemUnit(ItemUnit.CUP);
	tpl.setPrice(12.5f);
	Item item = new Item(tpl, "S1");
	Promotion p = new Promotion();
	p.setName("30% discount");
	p.setItems(item.getCode());
	p.setType(PromotionType.ITEM);
	p.setDetails("Discount 30%");
	p.setStoreCode("S1");
	RuleDefinition rule = new RuleDefinition("tastePrice");
	rule.addParameter(new RuleParameter(TastePriceRule.TASTE_PRICE_PAR, "8.99"));
	p.setRule(rule);
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.DAY_OF_YEAR, 10);
	p.setValidTo(calendar.getTime());
	BigDecimal price = promotionExecutor.executeRule(item, p);
	TestCase.assertEquals(8.99f, price.floatValue());
    }

    @Test
    public void testItemDiscountRule() {
	ItemTemplate tpl = new ItemTemplate();
	tpl.setCode("A0001");
	tpl.setName("Icecream");
	tpl.setCategories("Sweet", "Cold");
	tpl.setItemUnit(ItemUnit.CUP);
	tpl.setPrice(12.5f);
	Item item = new Item(tpl, "S1");
	Promotion p = new Promotion();
	p.setName("30% discount");
	p.setItems(item.getCode());
	p.setType(PromotionType.ITEM);
	p.setDetails("Discount 30%");
	p.setStoreCode("S1");
	RuleDefinition rule = new RuleDefinition("itemDiscount");
	rule.addParameter(new RuleParameter(ItemDiscountRule.DISCOUNT_PAR, "3"));
	p.setRule(rule);
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.DAY_OF_YEAR, 10);
	p.setValidTo(calendar.getTime());
	BigDecimal price = promotionExecutor.executeRule(item, p);
	TestCase.assertEquals((float) (12.5 * 0.3), price.floatValue());
    }
}
