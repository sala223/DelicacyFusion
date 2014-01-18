package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.Promotion.PromotionType;

public interface PromotionService {

	Promotion createPromotion(String storeCode, Promotion promotion);

	Promotion updatePromotion(String storeCode, Promotion promotion);

	int deletePromotion(String storeCode, String promotionCode);

	List<Promotion> getPromotionsByType(String storeCode, PromotionType type);

	List<Promotion> getValidPromotionsByType(String storeCode, PromotionType type);

	Promotion getPromotionByCode(String storeCode, String code);

}
