package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

@Entity
public class Promotion extends StoreAwareMasterData {
    public static enum PromotionType {
	ITEM, CATEGORY, ORDER,
    }

    @Column(nullable = false, name = "NAME", length = 128)
    private String name;

    @ElementCollection(targetClass = String.class)
    @Column(name = "ITEM_CODE", length = 255)
    @JoinFetch(JoinFetchType.OUTER)
    @CollectionTable(name = "PROMOTION_ITEM", joinColumns = @JoinColumn(name = "PROMOTION_ID"))
    private List<String> items;

    @ElementCollection(targetClass = String.class)
    @Column(name = "CATEGORY_CODE", length = 255)
    @JoinFetch(JoinFetchType.OUTER)
    @CollectionTable(name = "PROMOTION_CATEGORY", joinColumns = @JoinColumn(name = "PROMOTION_ID"))
    private List<String> categories;

    @Column(nullable = false, name = "PROMOTION_TYPE")
    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "VALID_FROM")
    private Date validFrom;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "VALID_TO")
    private Date validTo;

    @Column(nullable = false, name = "DETAILS")
    private String details;

    @Column(name = "QUALIFIER", length = 128, nullable = false)
    private String ruleQualifier;

    @ElementCollection
    @CollectionTable(name = "RULE_PARAMETER", joinColumns = @JoinColumn(name = "PROMOTION_ID"))
    @MapKey(name = "name")
    private Map<String, RuleParameter> ruleParameters = new HashMap<String, RuleParameter>();

    public Date getValidFrom() {
	return validFrom;
    }

    public void setValidFrom(Date validFrom) {
	this.validFrom = validFrom;
    }

    public Date getValidTo() {
	return validTo;
    }

    public void setValidTo(Date validTo) {
	this.validTo = validTo;
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

    public RuleDefinition getRule() {
	RuleDefinition rule = new RuleDefinition(this.ruleQualifier);
	rule.setParameters(this.ruleParameters);
	return rule;
    }

    public void setRule(RuleDefinition rule) {
	this.ruleQualifier = rule.getQualifier();
	this.ruleParameters = rule.getParameters();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setCategories(String... categories) {
	if (this.categories == null) {
	    this.categories = new ArrayList<String>();
	} else {
	    this.categories.clear();
	}
	addCategories(categories);
    }

    public void addCategories(String... categories) {
	if (this.categories == null) {
	    this.categories = new ArrayList<String>();
	}
	if (categories != null) {
	    for (String c : categories) {
		this.categories.add(c);
	    }
	}
    }

    public void setItems(String... items) {
	if (this.items == null) {
	    this.items = new ArrayList<String>();
	} else {
	    this.items.clear();
	}
	addItems(items);
    }

    public void addItems(String... items) {
	if (this.items == null) {
	    this.items = new ArrayList<String>();
	}
	if (items != null) {
	    for (String item : items) {
		this.items.add(item);
	    }
	}
    }

    @Override
    protected void fillDefaultValue() {
	super.fillDefaultValue();
	this.validFrom = new Date();
    }
}
