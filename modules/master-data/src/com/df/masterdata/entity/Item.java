package com.df.masterdata.entity;

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
import javax.persistence.OneToMany;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Cache
@Entity
@Index(name = "item_code_index", unique = true, columnNames = { "code", MultiTenantSupport.TENANT_COLUMN })
public class Item extends MasterData {

    @Column(length = 16)
    private String code;

    @Column(length = 128)
    @Index(name = "item_name_index", unique = false, columnNames = { "name", MultiTenantSupport.TENANT_COLUMN })
    private String name;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_category")
    private List<Category> Categories;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item_pictures", joinColumns = @JoinColumn(name = "ITEM_ID"))
    @Column
    private Set<PictureRef> pictureSet;

    @Lob
    private String description;

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
	return Categories;
    }

    public void setCategories(List<Category> categories) {
	Categories = categories;
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
}
