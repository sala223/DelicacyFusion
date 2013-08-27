package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Promotion extends MasterData {

    public static enum PromotionType {
	ITEM, CATEGORY, ORDERA,
    }

    @Column(nullable = false)
    private long storeId;

    @Column
    private String itemCode;

    @Column
    private long categoryId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @Column
    private String details;

    private String ruleId;

    public long getStoreId() {
	return storeId;
    }

    public void setStoreId(long storeId) {
	this.storeId = storeId;
    }

    public String getItemCode() {
	return itemCode;
    }

    public void setItemCode(String itemCode) {
	this.itemCode = itemCode;
    }

    public long getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(long categoryId) {
	this.categoryId = categoryId;
    }

    public PromotionType getType() {
	return type;
    }

    public void setType(PromotionType type) {
	this.type = type;
    }

    public String getDetails() {
	return details;
    }

    public void setDetails(String details) {
	this.details = details;
    }

    public String getRuleId() {
	return ruleId;
    }

    public void setRuleId(String ruleId) {
	this.ruleId = ruleId;
    }
}
