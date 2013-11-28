package com.df.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.order.dao.PaymentDao;
import com.df.order.entity.Order;
import com.df.order.entity.Payment;
import com.df.order.entity.PaymentLine;
import com.df.order.entity.TransactionStatus;
import com.df.order.entity.PaymentLine.PaymentMethod;
import com.df.order.exception.OrderException;
import com.df.order.exception.PaymentException;
import com.df.order.payment.Biller;
import com.df.order.payment.CashBiller;
import com.df.order.service.contract.OrderService;
import com.df.order.service.contract.PaymentService;

@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private PaymentDao paymentDao;
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	@Override
	public Payment createPayment(String storeCode, long orderId) {
		Payment payment = this.getPaymentByOrder(storeCode, orderId);
		if (payment != null) {
			return payment;
		} else {
			Order order = orderService.getOrderById(storeCode, orderId);
			if (order == null) {
				String message = "Order " + orderId + " is not found";
				throw OrderException.orderNotFound(message);
			}
			payment = new Payment();
			payment.setStoreCode(storeCode);
			payment.setOrderId(order.getTransactionId());
			payment.setTotalAmount(order.getTotalPaymentAfterDiscount());
			paymentDao.insert(payment);
			orderService.updateOrderStatus(storeCode, order, TransactionStatus.WAIT_TO_PAY);
			return payment;
		}
	}

	@Override
	public PaymentLine bill(String storeCode, long paymentId, BigDecimal amount, PaymentMethod method) {
		Payment payment = paymentDao.getPaymentById(storeCode, paymentId);
		if (payment == null) {
			String message = "Payment " + paymentId + " is not found";
			throw PaymentException.paymentNotFound(message);
		}
		Biller biller = null;
		if (method == PaymentMethod.CASH) {
			biller = new CashBiller();
		}
		PaymentLine line = biller.bill(payment, amount);
		paymentDao.update(payment);
		return line;
	}

	@Override
	public Payment getPaymentByOrder(String storeCode, long orderId) {
		return paymentDao.getPaymentByOrder(storeCode, orderId);
	}

	@Override
	public Payment bill(String storeCode, long orderId, PaymentMethod method) {
		Order order = orderService.getOrderById(storeCode, orderId);
		if (order == null) {
			String message = "Order " + orderId + " is not found";
			throw OrderException.orderNotFound(message);
		}
		Payment payment = paymentDao.getPaymentByOrder(storeCode, orderId);
		if (payment != null) {
			BigDecimal totalAmount = payment.getTotalAmount();
			BigDecimal lineTotalAmount = payment.getLineTotalAmount();
			BigDecimal leftAmount = totalAmount.subtract(lineTotalAmount);
			this.bill(storeCode, payment.getTransactionId(), leftAmount, method);
 		} else {
			payment = this.createPayment(storeCode, orderId);
			paymentDao.flush();
			long paymentId = payment.getTransactionId();
			this.bill(storeCode, paymentId, order.getTotalPaymentAfterDiscount(), method);
		}
		payment.setStatus(TransactionStatus.CLOSED);
		payment.setCloseTime(new Date()); 
		paymentDao.update(payment);
		orderService.updateOrderStatus(storeCode, order, TransactionStatus.CLOSED);
		return payment;
	}

	@Override
    public Payment getPaymentById(String storeCode, long paymentId) {
	    return paymentDao.getPaymentById(storeCode, paymentId);
    }

	@Override
    public List<Payment> listTodayOpenPayments(String storeCode, Locale locale) {
	    return paymentDao.listTodayOpenPayments(storeCode,Locale.CHINESE);
    }
}
