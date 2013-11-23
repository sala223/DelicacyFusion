package com.df.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class PaymentLine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, name = "LINE_NUMBER")
	private long lineNumber;

	@Column(name = "AMOUNT", scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "METHOD", length = 32)
	private PaymentMethod method;

	@Column(name = "EXTERNAL_TRANSACTION_ID")
	private long externalTransactionId;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "TIME_OF_PAYMENT")
	private Date timeOfPayment;

	public long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PaymentMethod getMethod() {
		return method;
	}

	public void setMethod(PaymentMethod method) {
		this.method = method;
	}

	public long getExternalTransactionId() {
		return externalTransactionId;
	}

	public void setExternalTransactionId(long externalTransactionId) {
		this.externalTransactionId = externalTransactionId;
	}

	public Date getTimeOfPayment() {
		return timeOfPayment;
	}

	public void setTimeOfPayment(Date timeOfPayment) {
		this.timeOfPayment = timeOfPayment;
	}

	public static enum PaymentMethod {
		CASH, POS
	}
}
