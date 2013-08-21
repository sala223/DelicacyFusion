package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;
import com.df.masterdata.entity.Constants.ITEM_TEMPLATE;

@Cache
@Entity
@Index(name = "item_code_index", unique = true, columnNames = { "code", MultiTenantSupport.TENANT_COLUMN })
@Table(name = ITEM_TEMPLATE.TABLE_NAME)
public class ItemTemplate extends MasterData {

    @Column(length = 16)
    private String code;

    @Column(length = 128)
    @Index(name = "item_name_index", unique = false, columnNames = { "name", MultiTenantSupport.TENANT_COLUMN })
    private String name;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_template_category")
    private List<Category> categories;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item_template_pictures", joinColumns = @JoinColumn(name = "ITEM_ID"))
    @Column
    private Set<PictureRef> pictureSet;

    @Lob
    private String description;

    @Column(precision = 2)
    private float price;

    @Enumerated(EnumType.STRING)
    @Column
    private PriceUnit priceUnit;

    @Enumerated(EnumType.STRING)
    @Column
    private ItemUnit itemUnit;

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Category> getCategories() {
	return categories;
    }

    public void setCategories(List<Category> categories) {
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

    public PriceUnit getPriceUnit() {
	return priceUnit;
    }

    public void setPriceUnit(PriceUnit priceUnit) {
	this.priceUnit = priceUnit;
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
	    this.type = ItemType.Food;
	}
	if (this.itemUnit == null) {
	    this.itemUnit = ItemUnit.DISK;
	}
    }

    public void setCategories(Category... categories) {
	if (categories != null) {
	    this.categories = Arrays.asList(categories);
	}
    }

    public void addCategories(Category... categories) {
	if (this.categories != null) {
	    this.categories = new ArrayList<Category>();
	}
	if (categories != null) {
	    for (Category c : categories) {
		this.categories.add(c);
	    }
	}
    }
}
