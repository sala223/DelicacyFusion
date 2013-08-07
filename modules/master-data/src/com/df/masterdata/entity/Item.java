package com.df.masterdata.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.eclipse.persistence.annotations.Cache;

import com.df.core.entity.MasterData;

@Cache
@Entity(name = "ITEM")
public class Item extends MasterData {

    @Column(length = 16)
    private String code;

    @Column(length = 128)
    private String name;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ITEM_CATEGORY")
    private List<Category> Categories;

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
}
