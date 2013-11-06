package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;
import com.df.masterdata.auxiliary.template.CategoryProfile;

@Entity
@JsonIgnoreProperties({ "enabled" })
@Index(name = "IDX_CATEGORY_T_CODE", unique = true, columnNames = { "code", MultiTenantSupport.TENANT_COLUMN })
@XmlRootElement
public class Category extends MasterData {

    @Column(length = 128, name = "NAME")
    private String name;

    public Category() {
    }

    public Category(CategoryProfile profile) {
	this.setProfile(profile);
    }

    public void setProfile(CategoryProfile profile) {
	this.setCode(profile.getCode());
	this.setName(profile.getName());
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
