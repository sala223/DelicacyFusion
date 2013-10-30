package com.df.masterdata.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.df.masterdata.entity.Constants.PROMOTION;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.masterdata.entity.Promotion;

public class PromotionDao extends StoreAwareMasterDataAccessFoundation {

    public void createPromotion(Promotion promotion) {
	if (promotion.getRule() == null) {
	    throw new NullPointerException("Rule property must not be null");
	}
	this.insert(promotion);
    }

    public List<Promotion> getValidItemsPromotions(String storeCode) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Promotion> query = builder.createQuery(Promotion.class);
	Root<Promotion> root = query.from(Promotion.class);
	Predicate equalStore = builder.equal(root.get(PROMOTION.STORE_CODE_PROPERTY), storeCode);
	Expression<java.sql.Timestamp> now = builder.currentTimestamp();
	Path<java.sql.Timestamp> validFromPath = root.get(PROMOTION.VALID_FROM_PROPERTY);
	Path<java.sql.Timestamp> validToPath = root.get(PROMOTION.VALID_TO_PROPERTY);
	Predicate timeBetween = builder.between(now, validFromPath, validToPath);
	Predicate type1Equal = builder.equal(root.get(PROMOTION.TYPE_PROPERTY), PromotionType.ITEM);
	Predicate type2Equal = builder.equal(root.get(PROMOTION.TYPE_PROPERTY), PromotionType.CATEGORY);
	query.where(equalStore, timeBetween, builder.or(type1Equal, type2Equal));
	return executeQuery(query);
    }

    public List<Promotion> getValidPromotionsByType(String storeCode, PromotionType type) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Promotion> query = builder.createQuery(Promotion.class);
	Root<Promotion> root = query.from(Promotion.class);
	Predicate equalStore = builder.equal(root.get(PROMOTION.STORE_CODE_PROPERTY), storeCode);
	Expression<java.sql.Timestamp> now = builder.currentTimestamp();
	Path<java.sql.Timestamp> validFromPath = root.get(PROMOTION.VALID_FROM_PROPERTY);
	Path<java.sql.Timestamp> validToPath = root.get(PROMOTION.VALID_TO_PROPERTY);
	Predicate timeBetween = builder.between(now, validFromPath, validToPath);
	Predicate typeEqual = builder.equal(root.get(PROMOTION.TYPE_PROPERTY), type);
	query.where(equalStore, timeBetween, typeEqual);
	return executeQuery(query);
    }

    public List<Promotion> getValidItemPromotions(Item item) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Promotion> query = builder.createQuery(Promotion.class);
	Root<Promotion> root = query.from(Promotion.class);
	Predicate equalStore = builder.equal(root.get(PROMOTION.STORE_CODE_PROPERTY), item.getStoreCode());
	Expression<java.sql.Timestamp> now = builder.currentTimestamp();
	Path<java.sql.Timestamp> validFromPath = root.get(PROMOTION.VALID_FROM_PROPERTY);
	Path<java.sql.Timestamp> validToPath = root.get(PROMOTION.VALID_TO_PROPERTY);
	Predicate timeBetween = builder.between(now, validFromPath, validToPath);
	Predicate itemTypeEqual = builder.equal(root.get(PROMOTION.TYPE_PROPERTY), PromotionType.ITEM);
	Path<List<String>> itemsPath = root.get(PROMOTION.ITEMS_PROPERTY);
	Predicate itemIn = builder.isMember(item.getCode(), itemsPath);
	Predicate itemMatch = builder.and(itemTypeEqual, itemIn);
	List<Predicate> categoriesIn = new ArrayList<Predicate>();
	categoriesIn.add(itemMatch);
	List<String> categories = item.getCategories();
	if (categories != null && categories.size() > 0) {
	    Predicate categoryTypeMatch = builder.equal(root.get(PROMOTION.TYPE_PROPERTY), PromotionType.CATEGORY);
	    Path<List<String>> categoriesPath = root.get(PROMOTION.CATEGORIES_PROPERTY);
	    for (int i = 0; i < categories.size(); i++) {
		categoriesIn.add(builder.and(categoryTypeMatch, builder.isMember(categories.get(i), categoriesPath)));
	    }
	}

	query.where(equalStore, timeBetween, builder.or(categoriesIn.toArray(new Predicate[0])));
	return executeQuery(query);
    }
}
