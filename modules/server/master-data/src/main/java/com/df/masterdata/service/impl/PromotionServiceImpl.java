package com.df.masterdata.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.df.core.validation.exception.ValidationException;
import com.df.masterdata.dao.PromotionDao;
import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.RuleDefinition;
import com.df.masterdata.entity.StoreObjectId;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.masterdata.exception.ItemException;
import com.df.masterdata.exception.PromotionException;
import com.df.masterdata.promotion.rule.RuleRepository;
import com.df.masterdata.promotion.rule.RuleValidator;
import com.df.masterdata.promotion.rule.descriptor.RuleDescriptor;
import com.df.masterdata.promotion.rule.descriptor.RuleDescriptor.RuleType;
import com.df.masterdata.service.contract.ItemService;
import com.df.masterdata.service.contract.PromotionService;

@Transactional
public class PromotionServiceImpl implements PromotionService {

	private RuleRepository ruleRepository;

	private PromotionDao promotionDao;

	@Autowired
	private Validator validator;

	private RuleValidator ruleValidator;

	private ItemService itemService;

	public void setRuleRepository(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	public void setPromotionDao(PromotionDao promotionDao) {
		this.promotionDao = promotionDao;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setRuleValidator(RuleValidator ruleValidator) {
		this.ruleValidator = ruleValidator;
	}

	@Override
	public Promotion createPromotion(String storeCode, Promotion promotion) {
		promotion.setStoreCode(storeCode);
		this.checkPromotion(promotion);
		promotionDao.createPromotion(promotion);
		return promotion;
	}

	private void checkPromotion(Promotion promotion) {
		Set<ConstraintViolation<Promotion>> violations = validator.validate(promotion);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		RuleDefinition rule = promotion.getRule();
		RuleDescriptor ruleDescriptor = ruleRepository.getRuleByQualifier(rule.getQualifier());
		if (ruleDescriptor == null) {
			throw PromotionException.ruleIsNotFound(rule.getQualifier());
		} else {
			if (!isPromotionTypeMatchRuleType(ruleDescriptor, promotion.getType())) {
				throw PromotionException.ruleTypeMisMatch(promotion.getType());
			}
			if (promotion.getType() == PromotionType.ITEM) {
				if (promotion.getItems() == null || promotion.getItems().size() == 0) {
					throw PromotionException.atLeastOneItemToBeSpecified();
				}
				List<String> unavaliableItems = itemService.listUnavaliableItems(promotion.getStoreCode(),
				        promotion.getItems());
				if (unavaliableItems != null && unavaliableItems.size() > 0) {
					throw ItemException.itemWithCodeNotExist(unavaliableItems.get(0));
				}
			} else if (promotion.getType() == PromotionType.CATEGORY) {
				if (promotion.getCategories() == null || promotion.getCategories().size() == 0) {
					throw PromotionException.atLeastOneCategoryToBeSpecified();
				}
			}
			ruleValidator.validate(rule);
		}
	}

	private boolean isPromotionTypeMatchRuleType(RuleDescriptor ruleDescriptor, PromotionType pt) {
		RuleType type = ruleDescriptor.getType();
		if (type == RuleType.ITEM || type == RuleType.ALL) {
			if (pt != PromotionType.ITEM && pt != PromotionType.CATEGORY) {
				return false;
			} else {
				return true;
			}
		} else {
			if (pt != PromotionType.ORDER) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public Promotion updatePromotion(String storeCode, Promotion promotion) {
		promotion.setStoreCode(storeCode);
		Promotion found = promotionDao.getPromotionByCode(storeCode, promotion.getCode());
		if (found == null) {
			throw PromotionException.promitionWithCodeNotExist(promotion.getCode());
		}
		this.checkPromotion(promotion);
		promotionDao.update(promotion);
		return promotion;
	}

	@Override
	public int deletePromotion(String storeCode, String promotionCode) {
		Assert.notNull(storeCode);
		Assert.notNull(promotionCode);
		return promotionDao.remove(Promotion.class, new StoreObjectId(storeCode, promotionCode));
	}

	@Override
	public List<Promotion> getPromotionsByType(String storeCode, PromotionType type) {
		return promotionDao.getPromotionsByType(storeCode, type);
	}

	@Override
	public List<Promotion> getValidPromotionsByType(String storeCode, PromotionType type) {
		return promotionDao.getValidPromotionsByType(storeCode, type);
	}

	@Override
	public Promotion getPromotionByCode(String storeCode, String code) {
		return promotionDao.getPromotionByCode(storeCode, code);
	}

}
