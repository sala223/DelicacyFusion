package com.df.order.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.order.entity.ServiceCard;
import com.df.order.service.contract.ServiceCardService;

public class TableBookingControllerImpl implements TableBookingController {

	@Autowired
	private ServiceCardService serviceCardService;

	public void setServiceCardService(ServiceCardService serviceCardService) {
		this.serviceCardService = serviceCardService;
	}

	@Override
	public ServiceCard acquireTables(String storeCode, List<String> tables) {
		return serviceCardService.createServiceCard(storeCode, tables);
	}

	@Override
	public void releaseTables(String storeCode, long serviceCardId) {
		serviceCardService.deleteServiceCard(storeCode, serviceCardId);
	}

}
