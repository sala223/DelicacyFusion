package com.df.order.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
public class Order extends MultiTenantSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_sequence")
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence")
    private long orderId;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long storeId;

    @Temporal(value = TemporalType.TIME)
    @Column(nullable = false)
    private Date tradeTime;

    @Temporal(value = TemporalType.TIME)
    @Column(nullable = true)
    private Date closeTime;

    @OneToMany
    private List<OrderLine> lines;

    @Column
    private float totalPayment;

    @Column
    private Payment payment;

    @Column
    private float discount;

    @Column
    private float deposit;

    @Column(length = 32)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    private String promotionDetails;

    @Column
    private String comment;

    public long getOrderId() {
	return orderId;
    }

    public void setOrderId(long orderId) {
	this.orderId = orderId;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public long getStoreId() {
	return storeId;
    }

    public void setStoreId(long storeId) {
	this.storeId = storeId;
    }

    public Date getTradeTime() {
	return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
	this.tradeTime = tradeTime;
    }

    public Date getCloseTime() {
	return closeTime;
    }

    public void setCloseTime(Date closeTime) {
	this.closeTime = closeTime;
    }

    public List<OrderLine> getLines() {
	return lines;
    }

    public void setLines(List<OrderLine> lines) {
	this.lines = lines;
    }

    public float getTotalPayment() {
	return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
	this.totalPayment = totalPayment;
    }

    public Payment getPayment() {
	return payment;
    }

    public void setPayment(Payment payment) {
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

    public OrderStatus getStatus() {
	return status;
    }

    public void setStatus(OrderStatus status) {
	this.status = status;
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
}
