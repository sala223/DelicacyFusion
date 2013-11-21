package com.df.client.rs.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends TransactionEntity {

	private long orderId;

	private Date dinnerTime;

	private boolean isTakeOut;

	private List<OrderLine> lines = new ArrayList<OrderLine>();

	private BigDecimal totalPayment;

	private String currency;

	private BigDecimal discount;

	private float deposit;

	private float serviceFee;

	private float otherFee;

	private String promotionName;

	private String comment;

	private long serviceCardId;

	@Override
	public long getTransactionId() {
		return orderId;
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

	public int getItemCount() {
		int count = 0;
		for (OrderLine line : lines)
			count += line.getQuantity();

		return count;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
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

	public long getServiceCardId() {
		return serviceCardId;
	}

	public void setServiceCardId(long serviceCardId) {
		this.serviceCardId = serviceCardId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	// public int addOrderLine(OrderLine line) {
	// if (this.lines == null) {
	// this.lines = new ArrayList<OrderLine>();
	// }
	// int lineNumer = this.lines.size() + 1;
	// line.setLineNumber(lineNumer);
	// this.lines.add(line);
	// return lineNumer;
	// }

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

	// public boolean removeOrderLine(int lineNumber) {
	// int foundIndex = -1;
	// if (this.lines == null) {
	// for (int i = 0; i < lines.size(); ++i) {
	// if (lines.get(i).getLineNumber() == lineNumber) {
	// foundIndex = i;
	// continue;
	// }
	// if (foundIndex != -1) {
	// lines.get(i).setLineNumber(i);
	// }
	// }
	// if (foundIndex != -1) {
	// this.lines.remove(foundIndex);
	// }
	// }
	// return foundIndex != -1;
	// }

	public void consolidateLine() {
		if (this.lines == null) {
			this.lines = new ArrayList<OrderLine>();
		}
	}

	public void calculatePayment() {

	}
}
