package com.df.order.payment;

import java.math.BigDecimal;
import java.util.Date;

import com.df.order.entity.Payment;
import com.df.order.entity.PaymentLine;
import com.df.order.entity.PaymentLine.PaymentMethod;
import com.df.order.exception.PaymentException;

public class CashBiller implements Biller {

	@Override
	public PaymentLine bill(Payment payment, BigDecimal amount) {
		BigDecimal lineTotalAmount = payment.getLineTotalAmount();
		if (amount.compareTo(payment.getTotalAmount().subtract(lineTotalAmount)) > 0) {
			throw PaymentException.billExceedsOrderTotal();
		}
		PaymentLine line = new PaymentLine();
		line.setMethod(PaymentMethod.CASH);
		line.setAmount(amount);
		line.setTimeOfPayment(new Date());
		payment.addPaymentLine(line);
		return line;
	}

}
