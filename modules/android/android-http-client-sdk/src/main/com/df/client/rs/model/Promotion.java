package com.df.client.rs.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Promotion extends MasterData {

    public static enum PromotionType {
	ITEM, CATEGORY, ORDER,
    }

    private String name;

    private List<String> items;

    private List<String> categories;

    private PromotionType type;

    private Date validFrom;

    private Date validTo;

    private String details;

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
