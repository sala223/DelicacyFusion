package com.df.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.df.masterdata.entity.Item;

@Embeddable
public class OrderLine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, name = "LINE_NUMBER")
	private int lineNumber;

	@Column(nullable = false, name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "TABLE_CODE")
	private String tableCode;

	@Column(name = "PRICE", scale = 2)
	private float price;

	@Column(name = "PROMOTION_PRICE", scale = 2)
	private Float promotionPrice;

	@Column(name = "DISCOUNT", scale = 2)
	private BigDecimal discount;

	@Column(name = "QUANTITY")
	private int quantity;

	@Column(name = "TOTAL_PAYMENT", scale = 2)
	private BigDecimal totalPayment;

	@Column(name = "TOTAL_PAYMENT_AFTER_DISCOUNT", scale = 2)
	private BigDecimal totalPaymentAfterDiscount;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "PROMOTION_NAME")
	private String promotionName;

	OrderLine() {
	}

	public OrderLine(Item item, int quantity) {
		this.itemCode = item.getCode();
		this.quantity = quantity;
		this.price = item.getPrice();
	}

	public OrderLine(String itemCode, int quantity) {
		this.itemCode = itemCode;
		this.quantity = quantity;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public Float getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(float promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public BigDecimal getTotalPaymentAfterDiscount() {
		if (discount != null) {
			return this.totalPayment.subtract(discount);
		} else {
			return this.totalPayment;
		}
	}

	public boolean hasPromotion() {
		return this.promotionName != null;
	}

	public void calcuatePayment() {
		this.totalPayment = new BigDecimal(this.price).multiply(new BigDecimal(quantity));
		if (this.hasPromotion()) {
			this.discount = new BigDecimal(this.promotionPrice).multiply(new BigDecimal(quantity));
			this.totalPaymentAfterDiscount = this.totalPayment.subtract(this.discount);
		} else {
			this.totalPaymentAfterDiscount = this.totalPayment;
		}
	}
}
