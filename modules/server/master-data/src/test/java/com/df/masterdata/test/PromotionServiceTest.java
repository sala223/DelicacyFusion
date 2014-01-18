package com.df.masterdata.test;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.core.validation.exception.ValidationException;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.masterdata.entity.RuleDefinition;
import com.df.masterdata.entity.RuleParameter;
import com.df.masterdata.exception.PromotionException;
import com.df.masterdata.promotion.rule.ItemDiscountRule;
import com.df.masterdata.service.contract.ItemService;
import com.df.masterdata.service.contract.ItemTemplateService;
import com.df.masterdata.service.contract.PromotionService;

@Transactional
public class PromotionServiceTest extends MasterDataJPABaseTest {

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemTemplateService itemTemplateService;

	@Test(expected = ValidationException.class)
	public void testCreatePromotionWithoutType() {
		Promotion promotion = new Promotion();
		promotion.setValidTo(new Date());
		promotion.setCode("p1");
		promotionService.createPromotion("S1", promotion);
	}

	@Test(expected = ValidationException.class)
	public void testCreatePromotionWithoutQualifier() {
		Promotion promotion = new Promotion();
		promotion.setValidTo(new Date());
		promotion.setCode("p1");
		promotion.setType(PromotionType.ITEM);
		promotionService.createPromotion("S1", promotion);
	}

	@Test
	public void testCreatePromotionWithInvalidRuleQualifier() {
		Promotion promotion = new Promotion();
		promotion.setValidTo(new Date());
		promotion.setCode("p1");
		promotion.setType(PromotionType.ITEM);
		RuleDefinition rule = new RuleDefinition("test");
		promotion.setRule(rule);
		try {
			promotionService.createPromotion("S1", promotion);
			TestCase.fail();
		} catch (PromotionException ex) {
			TestCase.assertEquals(PromotionException.RULE_IS_NOT_FOUND, ex.getErrorCode());
		}
	}

	@Test
	public void testCreatePromotionWithoutCategory() {
		Promotion promotion = new Promotion();
		promotion.setValidTo(new Date());
		promotion.setCode("p1");
		promotion.setType(PromotionType.CATEGORY);
		RuleDefinition rule = new RuleDefinition("itemDiscount");
		promotion.setRule(rule);
		try {
			promotionService.createPromotion("S1", promotion);
			TestCase.fail();
		} catch (PromotionException ex) {
			TestCase.assertEquals(PromotionException.AT_LEAST_ONE_CATEGORY, ex.getErrorCode());
		}
	}

	@Test
	public void testCreatePromotionWithoutItem() {
		Promotion promotion = new Promotion();
		promotion.setValidTo(new Date());
		promotion.setCode("p1");
		promotion.setType(PromotionType.ITEM);
		RuleDefinition rule = new RuleDefinition("itemDiscount");
		promotion.setRule(rule);
		try {
			promotionService.createPromotion("S1", promotion);
			TestCase.fail();
		} catch (PromotionException ex) {
			TestCase.assertEquals(PromotionException.AT_LEAST_ONE_ITEM, ex.getErrorCode());
		}
	}

	@Test
	public void testCreatePromotion() {
		ItemTemplate tpl = new ItemTemplate();
		tpl.setCode("testItem1");
		tpl.setCategories("SYS00001");
		tpl.setPrice(34);
		tpl.setName("temItem1");
		itemTemplateService.createItemTemplate(tpl);
		Item item = new Item(tpl, "S1");
		itemService.newItem("S1", item);
		Promotion promotion = new Promotion();
		promotion.setValidTo(new Date());
		promotion.setCode("p1");
		promotion.setType(PromotionType.ITEM);
		promotion.addItems("testItem1");
		RuleDefinition rule = new RuleDefinition("itemDiscount");
		rule.addParameter(new RuleParameter(ItemDiscountRule.DISCOUNT_PAR, "7"));
		promotion.setRule(rule);
		promotionService.createPromotion("S1", promotion);
		Promotion found = promotionService.getPromotionByCode("S1", promotion.getCode());
		TestCase.assertNotNull(found);
	}
}
