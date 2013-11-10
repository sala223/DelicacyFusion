package com.df.order.promotion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.idm.entity.User;
import com.df.masterdata.dao.PromotionDao;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.order.entity.Order;

public class DefaultPromotionRepository implements PromotionRepository {

    @Autowired
    private PromotionDao promotionDao;

    public DefaultPromotionRepository() {
    }

    public DefaultPromotionRepository(PromotionDao promotionDao) {
	this.promotionDao = promotionDao;
    }

    public void setPromotionDao(PromotionDao promotionDao) {
	this.promotionDao = promotionDao;
    }

    @Override
    public List<Promotion> getItemBestPromotions(User user, Item item) {
	return promotionDao.getValidItemPromotions(item);
    }

    @Override
    public List<Promotion> getOrderBestPromotions(User user, Order order) {
	return promotionDao.getValidPromotionsByType(order.getStoreCode(), PromotionType.ORDER);
    }

}
