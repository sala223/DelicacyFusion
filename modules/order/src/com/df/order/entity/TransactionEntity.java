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
public abstract class TransactionEntity  {

    @Column(nullable = false)
    private long ownerId;

    @Column(nullable = false)
    private long storeId;

    @Temporal(value = TemporalType.TIME)
    @Column(nullable = false)
    private Date createTime;

    @Temporal(value = TemporalType.TIME)
    @Column
    private Date closeTime;

    @Column(length = 32)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public long getOwnerId() {
	return ownerId;
    }

    public void setOwnerId(long ownerId) {
	this.ownerId = ownerId;
    }

    public long getStoreId() {
	return storeId;
    }

    public void setStoreId(long storeId) {
	this.storeId = storeId;
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
