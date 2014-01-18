package com.df.order.entity;

import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

@XmlRootElement
@Entity
@Table(name = "ORDERS")
public class Order extends TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ID_SEQUENCE")
	@SequenceGenerator(initialValue = 1, name = "ORDER_ID_SEQUENCE", sequenceName = "ORDER_ID_SEQUENCE")
	@Column(name = "ORDER_ID")
	private long orderId;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "DINNER_TIME")
	private Date dinnerTime;

	@Column(name = "IS_TAKE_OUT")
	private boolean isTakeOut;

	@ElementCollection
	@CollectionTable(name = "ORDER_LINE", joinColumns = @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID"))
	@JoinFetch(JoinFetchType.OUTER)
	private List<OrderLine> lines;

	@Column(name = "TOTAL_PAYMENT", scale = 2)
	private BigDecimal totalPayment;

	@Column(name = "TOTAL_PAYMENT_AFTER_DISCOUNT", scale = 2)
	private BigDecimal totalPaymentAfterDiscount;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "DISCOUNT")
	private BigDecimal discount;

	@Column(name = "DEPOSIT")
	private float deposit;

	@Column(name = "SERVICE_FEE")
	private float serviceFee;

	@Column(name = "OTHER_FEE")
	private float otherFee;

	@Column(name = "PROMOTION_NAME", length = 128)
	private String promotionName;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "SERVICE_CARD_ID")
	private Long serviceCardId;

	@Override
	public long getTransactionId() {
		return orderId;
	}

	public Long getServiceCardId() {
		return serviceCardId;
	}

	public void setServiceCardId(Long serviceCardId) {
		this.serviceCardId = serviceCardId;
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

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPaymentAfterDiscount(BigDecimal totalPaymentAfterDiscount) {
		this.totalPaymentAfterDiscount = totalPaymentAfterDiscount;
	}

	public BigDecimal getTotalPaymentAfterDiscount() {
		if (this.totalPaymentAfterDiscount == null) {
			return this.totalPayment;
		} else {
			return totalPaymentAfterDiscount;
		}
	}

	public Date getDinnerTime() {
		return dinnerTime;
	}

	public void setDinnerTime(Date dinnerTime) {
		this.dinnerTime = dinnerTime;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
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
		for (OrderLine lin : lines) {
			if (line.getTableCode() == null && lin.getItemCode().equals(line.getItemCode())) {
				lin.setQuantity(lin.getQuantity() + line.getQuantity());
				return lin.getLineNumber();
			}
		}
		int lineNumer = this.lines.size() + 1;
		line.setLineNumber(lineNumer);
		this.lines.add(line);
		return lineNumer;
	}

	public float getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(float serviceFee) {
		this.serviceFee = serviceFee;
	}

	public float getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(float otherFee) {
		this.otherFee = otherFee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public boolean hasPromotion() {
		return this.promotionName != null;
	}

	public void calculatePayment() {
		if (this.totalPayment == null) {
			this.totalPayment = new BigDecimal(0);
		}
		if (this.lines != null) {
			for (OrderLine line : lines) {
				this.totalPayment = this.totalPayment.add(line.getTotalPaymentAfterDiscount());
			}
		}
		if (this.hasPromotion()) {
			this.discount = this.totalPayment.subtract(this.getTotalPaymentAfterDiscount());
		}
	}
}
