package com.df.order.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.df.core.persist.eclipselink.Property;
import com.df.order.entity.Constants.PAYMENT;
import com.df.order.entity.Payment;
import com.df.order.entity.TransactionStatus;

public class PaymentDao extends TransactionDataAccessFoundation {

	public Payment getPaymentByOrder(String storeCode, long orderId) {
		Property<String> p1 = new Property<String>(PAYMENT.STORE_CODE, storeCode);
		Property<Long> p2 = new Property<Long>(PAYMENT.ORDER_ID, orderId);
		return this.findSingleEntityByProperties(Payment.class, p1, p2);
	}

	public Payment getPaymentById(String storeCode, long paymentId) {
		Property<String> p1 = new Property<String>(PAYMENT.STORE_CODE, storeCode);
		Property<Long> p2 = new Property<Long>(PAYMENT.TRAN_ID, paymentId);
		return this.findSingleEntityByProperties(Payment.class, p1, p2);
	}

	public List<Payment> listTodayOpenPayments(String storeCode, Locale locale) {
		locale = locale == null ? Locale.CHINESE : locale;
		Calendar from = Calendar.getInstance(locale);
		from.setTime(new Date());
		from.set(Calendar.HOUR_OF_DAY, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		from.set(Calendar.MILLISECOND, 0);
		CriteriaBuilder builder = createQueryBuilder();
		CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
		Root<Payment> root = query.from(Payment.class);
		Predicate equalStore = builder.equal(root.get(PAYMENT.STORE_CODE), storeCode);
		Predicate equalStatus = builder.equal(root.get(PAYMENT.STATUS), TransactionStatus.OPEN);
		Path<java.sql.Timestamp> createFromPath = root.get(PAYMENT.CREATE_TIME);
		Predicate timeGreater = builder.greaterThanOrEqualTo(createFromPath, new Timestamp(from.getTime().getTime()));
		query.where(equalStore, equalStatus, timeGreater);
		return executeQuery(query);
	}
}
