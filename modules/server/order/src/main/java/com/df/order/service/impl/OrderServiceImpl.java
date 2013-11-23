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
import com.df.order.entity.ServiceCard;
import com.df.order.entity.TransactionStatus;
import com.df.order.exception.InvalidItemException;
import com.df.order.exception.OrderException;
import com.df.order.price.PaymentCalculator;
import com.df.order.price.PaymentContext;
import com.df.order.service.contract.OrderService;
import com.df.order.service.contract.ServiceCardService;

@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ItemService itemService;

	@Autowired
	private PaymentCalculator paymentCalculator;

	@Autowired
	private ServiceCardService serviceCardService;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setPaymentCalculator(PaymentCalculator paymentCalculator) {
		this.paymentCalculator = paymentCalculator;
	}

	public void setServiceCardService(ServiceCardService serviceCardService) {
		this.serviceCardService = serviceCardService;
	}

	@Override
	public void createOrder(String storeCode, long userId, Order order) {
		order.setStoreCode(storeCode);
		Long serviceCardId = order.getServiceCardId();
		if (serviceCardId != null) {
			serviceCardService.validateServiceCardId(storeCode, serviceCardId, true);
		}
		List<OrderLine> lines = order.getLines();
		if (lines == null) {
			throw OrderException.emptyLinesException();
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
		if (serviceCardId != null) {
			serviceCardService.associateServiceCardWithOrder(storeCode, serviceCardId, order);
		}
	}

	@Override
	public List<Order> getOrdersWithServiceCard(String storeCode, long userId) {
		List<ServiceCard> cards = serviceCardService.getAllServiceCard(storeCode);
		ArrayList<Long> orderIds = new ArrayList<Long>();
		for (ServiceCard card : cards) {
			if (card.getOrderId() != null) {
				orderIds.add(card.getOrderId());
			}
		}
		return orderDao.getOrderList(storeCode, userId, orderIds);
	}

	@Override
	public Order getOrderByTable(String storeCode, String tableCode) {
		ServiceCard card = serviceCardService.getServiceCardByTable(storeCode, tableCode);
		if (card == null) {
			return null;
		}
		Long orderId = card.getOrderId();
		if (orderId == null) {
			return null;
		}
		return orderDao.getOrderById(storeCode, orderId);
	}

	@Override
	public Order getOrderById(String storeCode, long orderId) {
		return orderDao.getOrderById(storeCode, orderId);
	}

	@Override
	public List<Order> getOrdersWithServiceCard(String storeCode) {
		List<ServiceCard> cards = serviceCardService.getAllServiceCard(storeCode);
		ArrayList<Long> orderIds = new ArrayList<Long>();
		for (ServiceCard card : cards) {
			if (card.getOrderId() != null) {
				orderIds.add(card.getOrderId());
			}
		}
		return orderDao.getOrderList(storeCode, orderIds);
	}

	@Override
	public void updateOrderStatus(String storeCode, Order order, TransactionStatus status) {
		order.setStoreCode(storeCode); 
		order.setStatus(status);
		if(status == TransactionStatus.CLOSED){
			order.setCloseTime(new Date()); 
		}
		orderDao.update(order);
	}
}
