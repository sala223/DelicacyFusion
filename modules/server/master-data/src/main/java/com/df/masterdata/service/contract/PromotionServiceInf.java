package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.Promotion;

public interface PromotionServiceInf {

    void createPromotion(Promotion promotion);

    List<Promotion> getItemValidPromotions();
}
