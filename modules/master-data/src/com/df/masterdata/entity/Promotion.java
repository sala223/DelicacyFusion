package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Promotion extends StoreAwareMasterData {

    public static enum PromotionType {
	ITEM, CATEGORY, ORDER,
    }

    @Column(nullable = false, name = "NAME", length = 128)
    private String name;

    @ElementCollection(targetClass = String.class)
    @Column(name = "ITEM_CODE", length = 255)
    @CollectionTable(name = "PROMOTION_ITEM", joinColumns = @JoinColumn(name = "PROMOTION_ID"))
    private List<String> items;

    @ElementCollection(targetClass = String.class)
    @Column(name = "CATEGORY_CODE", length = 255)
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

    @Column(nullable = false, name = "Details")
    private String details;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Rule rule;

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

    public Rule getRule() {
	return rule;
    }

    public void setRule(Rule rule) {
	this.rule = rule;
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
	addCategories(items);
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
}
