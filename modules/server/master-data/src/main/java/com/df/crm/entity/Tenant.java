package com.df.crm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Tenant implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{tenant.code.NotEmpty}")
	@Size(message = "{tenant.code.Size}", max = 64)
	@Id
	@Column(name = "CODE", length = 64, nullable = false)
	private String code;

	@NotEmpty(message = "{tenant.name.NotEmpty}")
	@Size(message = "{tenant.name.Size}", max = 128)
	@Column(name = "NAME", unique = true, length = 128, nullable = false)
	private String name;

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

	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@NotEmpty(message = "{tenant.address.NotEmpty}")
	@Size(message = "{tenant.address.Size}", max = 1024)
	@Column(name = "ADDRESS", length = 1024, nullable = false)
	private String address;

	@Column(name = "OWNER")
	private long owner;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	@PreUpdate
	protected void updateDefaultValue() {
		this.setChangedTime(new Date());
	}

}
