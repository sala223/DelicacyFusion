package com.df.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.entity.Promotion;
import com.df.masterdata.service.contract.ItemService;
import com.df.order.dao.OrderDao;
import com.df.order.entity.Order;
import com.df.order.entity.OrderLine;
import com.df.order.entity.TransactionStatus;
import com.df.order.exception.InvalidItemException;
import com.df.order.exception.OrderException;
import com.df.order.price.PaymentCalculator;
import com.df.order.price.PaymentContext;
import com.df.order.service.contract.OrderService;

@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ItemService itemService;

	@Autowired
	private PaymentCalculator paymentCalculator;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setPaymentCalculator(PaymentCalculator paymentCalculator) {
		this.paymentCalculator = paymentCalculator;
	}

	@Override
	public void createOrder(long userId, Order order) {
		List<OrderLine> lines = order.getLines();
		if (lines == null) {
			throw OrderException.emptyLinesException();
		}
		String storeCode = order.getStoreCode();
		if (storeCode == null) {
			throw OrderException.storeIsNotSpecifiedException();
		}
		ArrayList<String> itemCodes = new ArrayList<String>();
		for (OrderLine line : lines) {
			String item = line.getItemCode();
			itemCodes.add(item);
		}
		List<String> items = itemService.listUnavaliableItems(storeCode, itemCodes);
		if (!items.isEmpty()) {
			throw new InvalidItemException(items.get(0));
		}
		order.setOwnerId(userId);
		if (order.getStatus() == TransactionStatus.PRESERVED) {
			if (order.getDinnerTime() == null) {
				throw OrderException.noDiningTimeForPreservedOrder();
			}
		} else {
			order.setStatus(TransactionStatus.OPEN);
			if (order.getDinnerTime() == null) {
				order.setDinnerTime(new Date());
			}
		}
		PaymentContext context = paymentCalculator.createContext();
		context.setOrder(order);
		paymentCalculator.calculateOrderPayment(context);
		Promotion promotion = context.getOrderAppliedPromotion();
		if (promotion != null) {
			order.setPromotionName(promotion.getName());
		}
		orderDao.insert(order);
	}

	@Override
	public List<Order> getLatestOrders(long userId, int number) {
		return orderDao.getLatestOrders(userId, number);
	}

}
