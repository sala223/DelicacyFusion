package com.df.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@MappedSuperclass
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = MultiTenantSupport.TENANT_COLUMN, length = 12, contextProperty = MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY)
public abstract class TransactionEntity {

	@Column(nullable = false, name = "OWNER_ID")
	private long ownerId;

	@Column(nullable = false, name = "STORE_CODE")
	private String storeCode;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "CREATE_TIME")
	private Date createTime;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "CLOSE_TIME")
	private Date closeTime;

	@Column(length = 32, name = "STATUS")
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public abstract long getTransactionId();

	public abstract void setTransactionId(long transactionId);

	@PrePersist
	protected void fillDefaultValue() {
		if (this.createTime == null) {
			this.setCreateTime(new Date());
		}
		if (this.status == null) {
			this.status = TransactionStatus.OPEN;
		}
	}
}
