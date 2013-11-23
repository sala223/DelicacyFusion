package com.df.order.service.contract;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import com.df.order.entity.Payment;
import com.df.order.entity.PaymentLine;
import com.df.order.entity.PaymentLine.PaymentMethod;

public interface PaymentService {

	List<Payment> listTodayOpenPayments(String storeCode, Locale locale);

	Payment createPayment(String storeCode, long orderId);

	Payment getPaymentByOrder(String storeCode, long orderId);

	Payment getPaymentById(String storeCode, long paymentId);

	Payment bill(String storeCode, long orderId, PaymentMethod method);

	PaymentLine bill(String storeCode, long paymentId, BigDecimal amount, PaymentMethod methods);
}
