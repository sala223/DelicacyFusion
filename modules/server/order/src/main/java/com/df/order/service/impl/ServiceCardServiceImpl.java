package com.df.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.service.contract.DiningTableService;
import com.df.order.dao.ServiceCardDao;
import com.df.order.entity.Order;
import com.df.order.entity.ServiceCard;
import com.df.order.exception.ServiceCardException;
import com.df.order.service.contract.ServiceCardService;

@Transactional
public class ServiceCardServiceImpl implements ServiceCardService {

	@Autowired
	private ServiceCardDao serviceCardDao;

	@Autowired
	private DiningTableService tableService;

	public void setServiceCardDao(ServiceCardDao serviceCardDao) {
		this.serviceCardDao = serviceCardDao;
	}

	public void setDiningTableService(DiningTableService tableService) {
		this.tableService = tableService;
	}

	@Override
	public ServiceCard createServiceCard(String storeCode, List<String> tableCodes) {
		return serviceCardDao.createServiceCard(storeCode, tableCodes);
	}

	@Override
	public void updateServiceCardTables(String storeCode, long serviceCardId, List<String> tableCodes) {
		serviceCardDao.updateServiceCardTables(storeCode, serviceCardId, tableCodes);

	}

	@Override
	public void deleteServiceCard(String storeCode, long serviceCardId) {
		serviceCardDao.deleteServiceCard(storeCode, serviceCardId);

	}

	@Override
	public void associateServiceCardWithOrder(String storeCode, long serviceCardId, Order order) {
		serviceCardDao.associateServiceCardWithOrder(storeCode, serviceCardId, order);
	}

	@Override
	public ServiceCard getServiceCardById(String storeCode, long id) {
		return serviceCardDao.getServiceCardById(storeCode, id);
	}

	@Override
	public ServiceCard getServiceCardByOrderId(String storeCode, long orderId) {
		return serviceCardDao.getServiceCardByOrderId(storeCode, orderId);
	}

	@Override
	public ServiceCard getServiceCardByTable(String storeCode, String tableCode) {
		return serviceCardDao.getServiceCardByTable(storeCode, tableCode);
	}

	@Override
	public List<ServiceCard> getAllServiceCard(String storeCode) {
		return serviceCardDao.all(storeCode);
	}

	@Override
	public boolean validateServiceCardId(String storeCode, long id, boolean throwException) {
		ServiceCard card = this.getServiceCardById(storeCode, id);
		if (card == null && throwException) {
			throw new ServiceCardException(ServiceCardException.INVALID_SERVICE_CARD_ID, "Invalid service card id.");
		} else {
			return card != null;
		}
	}

	@Override
	public ServiceCard createServiceCard(String storeCode, String tableCode) {
		ArrayList<String> tableCodes = new ArrayList<String>();
		tableCodes.add(tableCode);
		return this.createServiceCard(storeCode, tableCodes);
	}

	@Override
	public List<String> getOccupiedTables(String storeCode) {
		return serviceCardDao.getOccupiedTables(storeCode);
	}
}
