package com.df.order.payment;

import java.math.BigDecimal;

import com.df.order.entity.Payment;
import com.df.order.entity.PaymentLine;

public interface Biller {

	PaymentLine bill(Payment payment, BigDecimal amount);
}
