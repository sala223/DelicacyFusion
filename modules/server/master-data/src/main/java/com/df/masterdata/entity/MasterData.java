package com.df.masterdata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@MappedSuperclass
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = MultiTenantSupport.TENANT_COLUMN, length = 12, contextProperty = MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY)
public abstract class MasterData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long id;

    @Column(length = 255, name = "CODE")
    @Index
    private String code;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "CREATED_TIME")
    private Date createdTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "CHANGED_TIME")
    private Date changedTime;

    @Column(name = "CREATED_BY")
    private long createdBy;

    @Column(name = "IS_ENABLED")
    private boolean isEnabled = true;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getCode() {
	return code;
    }

    /**
     * Code is unique for each tenant
     */
    public void setCode(String code) {
	this.code = code;
    }

    public Date getCreatedTime() {
	return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
	this.createdTime = createdTime;
    }

    public Date getChangedTime() {
	return changedTime;
    }

    public void setChangedTime(Date changedTime) {
	this.changedTime = changedTime;
    }

    public long getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(long createdBy) {
	this.createdBy = createdBy;
    }

    public boolean isEnabled() {
	return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
	this.isEnabled = isEnabled;
    }

    @PrePersist
    protected void fillDefaultValue() {
	if (this.createdTime == null) {
	    this.setCreatedTime(new Date());
	}
    }

    @PreUpdate
    protected void updateDefaultValue() {
	this.setChangedTime(new Date());
    }
}
