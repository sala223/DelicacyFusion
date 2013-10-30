package com.df.order.price;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.dao.ItemDao;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;
import com.df.order.promotion.PromotionExecutor;
import com.df.order.promotion.PromotionRepository;

public class PromotionRulePaymentCalculator extends AbstractPaymentCalculator {

    private PromotionExecutor executor;

    @Autowired
    private ItemDao itemDao;

    public PromotionRulePaymentCalculator(PromotionRepository repository, PromotionExecutor executor) {
	super(repository);
	this.executor = executor;
    }

    public void setItemDao(ItemDao itemDao) {
	this.itemDao = itemDao;
    }

    public void setPromotionExecutor(PromotionExecutor executor) {
	this.executor = executor;
    }

    @Override
    protected Item getItemByItemCode(String storeCode, String itemCode) {
	return itemDao.getItemByItemCode(storeCode, itemCode);
    }

    @Override
    protected BigDecimal calcuateWithPromotion(Item item, Promotion promotion) {
	return executor.executeRule(item, promotion);
    }

    @Override
    protected BigDecimal calcuateWithPromotion(Order order, Promotion promotion) {
	return executor.executeRule(order, promotion);
    }

}
