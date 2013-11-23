package com.df.order.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

@XmlRootElement
@Entity
public class Payment extends TransactionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_ID_SEQUENCE")
	@SequenceGenerator(name = "PAYMENT_ID_SEQUENCE", sequenceName = "PAYMENT_ID_SEQUENCE")
	@Column(name = "PAYMENT_ID")
	private long paymentId;

	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "TOTAL_AMOUNT", scale = 2)
	private BigDecimal totalAmount;

	@Column(name = "SPECIAL_DISCOUNT", scale = 2)
	private BigDecimal specialDiscount;

	@ElementCollection
	@CollectionTable(name = "PAYMENT_LINE", joinColumns = @JoinColumn(name = "PAYMENT_ID"))
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal total) {
		this.totalAmount = total;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(BigDecimal specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public int addPaymentLine(PaymentLine line) {
		if (this.lines == null) {
			this.lines = new ArrayList<PaymentLine>();
		}
		int lineNumer = this.lines.size() + 1;
		line.setLineNumber(lineNumer);
		this.lines.add(line);
		return lineNumer;
	}

	public BigDecimal getLineTotalAmount() {
		if (this.lines == null) {
			this.lines = new ArrayList<PaymentLine>();
		}
		BigDecimal lineTotal = new BigDecimal(0);
		for (PaymentLine line : lines) {
			lineTotal = lineTotal.add(line.getAmount());
		}
		return lineTotal;
	}
}
