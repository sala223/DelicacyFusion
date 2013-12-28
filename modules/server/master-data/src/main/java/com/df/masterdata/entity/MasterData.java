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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;
import org.hibernate.validator.constraints.NotEmpty;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@XmlRootElement
@MappedSuperclass
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = MultiTenantSupport.TENANT_COLUMN, length = 12, contextProperty = MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY)
public abstract class MasterData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	@JsonIgnore
	@XmlTransient
	private long id;

	@NotEmpty(message = "{masterdata.code.NotEmpty}")
	@Size(message = "{masterdata.code.Size}", max = 255)
	@Column(length = 255, name = "CODE", updatable = false)
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
		this.setCreatedTime(new Date());
	}

	@PreUpdate
	protected void updateDefaultValue() {
		this.setChangedTime(new Date());
	}
}
