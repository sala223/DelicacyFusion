package com.df.order.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class Payment extends TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_sequence")
    @SequenceGenerator(name = "payment_id_sequence", sequenceName = "payment_id_sequence")
    private long paymentId;

    @Column
    private boolean isDeposit;

    @Override
    public long getTransactionId() {
	return paymentId;
    }

    @Override
    public void setTransactionId(long transactionId) {
	this.paymentId = transactionId;
    }

    public boolean isDeposit() {
	return isDeposit;
    }

    public void setDeposit(boolean isDeposit) {
	this.isDeposit = isDeposit;
    }

}
