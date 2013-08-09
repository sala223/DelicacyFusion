package com.df.masterdata.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.BatchFetch;
import org.eclipse.persistence.annotations.BatchFetchType;
import org.eclipse.persistence.annotations.Index;

import com.df.core.common.entity.MultiTenantSupport;

@Entity
@Table(name = Constants.CATEGORY.ENTITY_TABLE)
@Index(name = "UNIQUE_INDEX", unique = true, columnNames = { "name", MultiTenantSupport.TENANT_COLUMN })
public class Category extends MasterData {
    @Column(length = 64)
    @Index
    private String name;

    @Column(length = 512)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "PARENT", nullable = true)
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORY", joinColumns = { @JoinColumn(name = "PARENT") }, inverseJoinColumns = { @JoinColumn(name = "ID") })
    @BatchFetch(BatchFetchType.JOIN)
    private List<Category> children;

    public Category() {
    }

    public Category(String name) {
	setName(name);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Category getParent() {
	return parent;
    }

    public void setParent(Category parent) {
	this.parent = parent;
    }

    public List<Category> getChildren() {
	return children;
    }

    public void setChildren(List<Category> children) {
	this.children = children;
    }
}
