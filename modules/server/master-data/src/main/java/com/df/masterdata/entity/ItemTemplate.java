package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@Indexes({
	@Index(name = "IDX_ITEM_TPL_T_CODE", unique = true, columnNames = { "CODE", MultiTenantSupport.TENANT_COLUMN }),
	@Index(name = "IDX_ITEM_TPL_T_NAME", unique = false, columnNames = { "NAME", MultiTenantSupport.TENANT_COLUMN }) })
@Table(name = "ITEM_TEMPLATE")
public class ItemTemplate extends MasterData {

    @Column(length = 128, name = "NAME")
    private String name;

    @Column(length = 64, name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @ElementCollection(targetClass = String.class)
    @Column(name = "CATEGORY_CODE", length = 255)
    @CollectionTable(name = "ITEM_TEMPLATE_CATEGORY", joinColumns = @JoinColumn(name = "ITEM_TEMPLATE_ID"))
    private List<String> categories;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ITEM_TEMPLATE_PICTURE", joinColumns = @JoinColumn(name = "ITEM_TEMPLATE_ID"))
    private Set<PictureRef> pictureSet;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(scale=2, name = "PRICE")
    private float price;

    @Column(name = "PRICE_UNIT")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "ITEM_UNIT")
    private ItemUnit itemUnit;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<String> getCategories() {
	return categories;
    }

    public void setCategories(List<String> categories) {
	this.categories = categories;
    }

    public ItemType getType() {
	return type;
    }

    public void setType(ItemType type) {
	this.type = type;
    }

    public Set<PictureRef> getPictureSet() {
	return pictureSet;
    }

    public void setPictureSet(Set<PictureRef> pictureSet) {
	this.pictureSet = pictureSet;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public ItemUnit getItemUnit() {
	return itemUnit;
    }

    public void setItemUnit(ItemUnit itemUnit) {
	this.itemUnit = itemUnit;
    }

    @Override
    protected void fillDefaultValue() {
	super.fillDefaultValue();
	if (this.type == null) {
	    this.type = ItemType.FOOD;
	}
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
}