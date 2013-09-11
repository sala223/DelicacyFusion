package com.df.order.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import com.df.masterdata.entity.PriceUnit;

@Entity
public class Payment extends TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_sequence")
    @SequenceGenerator(name = "payment_id_sequence", sequenceName = "payment_id_sequence")
    private long paymentId;

    @Enumerated(EnumType.STRING)
    @Column
    private PriceUnit priceUnit;

    @Column
    private float totalPrice;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "PAYMENT_ID"))
    @JoinFetch(JoinFetchType.OUTER)
    private List<PaymentLine> lines;

    @Override
    public long getTransactionId() {
	return paymentId;
    }

    @Override
    public void setTransactionId(long transactionId) {
	this.paymentId = transactionId;
    }

    public List<PaymentLine> getLines() {
	return lines;
    }

    public void setLines(List<PaymentLine> lines) {
	this.lines = lines;
    }

    public long getPaymentId() {
	return paymentId;
    }

    public void setPaymentId(long paymentId) {
	this.paymentId = paymentId;
    }

    public PriceUnit getPriceUnit() {
	return priceUnit;
    }

    public void setPriceUnit(PriceUnit priceUnit) {
	this.priceUnit = priceUnit;
    }

    public float getTotalPrice() {
	return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
	this.totalPrice = totalPrice;
    }
}
