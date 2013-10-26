package com.df.client.rs.model;

import java.util.Date;

public abstract class TransactionEntity {

    private long ownerId;

    private String storeCode;

    private Date createTime;

    private Date closeTime;

    private TransactionStatus status;

    public long getOwnerId() {
	return ownerId;
    }

    public String getStoreCode() {
	return storeCode;
    }

    public Date getCreateTime() {
	return createTime;
    }

    public Date getCloseTime() {
	return closeTime;
    }

    public TransactionStatus getStatus() {
	return status;
    }

    public abstract long getTransactionId();

}
