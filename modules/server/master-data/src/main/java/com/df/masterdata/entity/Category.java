package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.enunciate.json.JsonRootType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@XmlRootElement
@JsonRootType
@Entity
@JsonIgnoreProperties({ "id", "createdTime", "changedTime", "createdBy", "enabled" })
@Index(name = "IDX_CATEGORY_T_CODE", unique = true, columnNames = { "code", MultiTenantSupport.TENANT_COLUMN })
public class Category extends MasterData {

    @Column(length = 128, name = "NAME")
    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
