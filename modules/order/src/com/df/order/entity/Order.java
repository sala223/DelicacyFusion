package com.df.order.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Order extends TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_sequence")
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence")
    private long orderId;

    @Temporal(value = TemporalType.TIME)
    @Column(nullable = false)
    private Date dinnerTime;

    @Column
    private boolean isTakeOut;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "ORDER_ID"))
    private List<OrderLine> lines;

    @Column
    private float totalPayment;

    @OneToOne
    private Payment payment;

    @Column
    private float discount;

    @Column
    private float deposit;

    @Column
    private String promotionDetails;

    @Column
    private String comment;

    @Override
    public long getTransactionId() {
	return orderId;
    }

    @Override
    public void setTransactionId(long transactionId) {
	this.orderId = transactionId;
    }

    public boolean isTakeOut() {
	return isTakeOut;
    }

    public void setTakeOut(boolean isTakeOut) {
	this.isTakeOut = isTakeOut;
    }

    public List<OrderLine> getLines() {
	return lines;
    }

    void setLines(List<OrderLine> lines) {
	this.lines = lines;
    }

    public float getTotalPayment() {
	return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
	this.totalPayment = totalPayment;
    }

    public Date getDinnerTime() {
	return dinnerTime;
    }

    public void setDinnerTime(Date dinnerTime) {
	this.dinnerTime = dinnerTime;
    }

    public Payment getPayment() {
	return payment;
    }

    public void Payment(Payment payment) {
	this.payment = payment;
    }

    public float getDiscount() {
	return discount;
    }

    public void setDiscount(float discount) {
	this.discount = discount;
    }

    public float getDeposit() {
	return deposit;
    }

    public void setDeposit(float deposit) {
	this.deposit = deposit;
    }

    public String getPromotionDetails() {
	return promotionDetails;
    }

    public void setPromotionDetails(String promotionDetails) {
	this.promotionDetails = promotionDetails;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public int addOrderLine(OrderLine line) {
	if (this.lines == null) {
	    this.lines = new ArrayList<OrderLine>();
	}
	int lineNumer = this.lines.size() + 1;
	line.setLineNumber(lineNumer);
	this.lines.add(line);
	return lineNumer;
    }

    public boolean removeOrderLine(int lineNumber) {
	int foundIndex = -1;
	if (this.lines == null) {
	    for (int i = 0; i < lines.size(); ++i) {
		if (lines.get(i).getLineNumber() == lineNumber) {
		    foundIndex = i;
		    continue;
		}
		if (foundIndex != -1) {
		    lines.get(i).setLineNumber(i);
		}
	    }
	    if (foundIndex != -1) {
		this.lines.remove(foundIndex);
	    }
	}
	return foundIndex != -1;
    }
}
