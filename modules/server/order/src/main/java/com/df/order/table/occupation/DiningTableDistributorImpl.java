package com.df.order.table.occupation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.service.contract.DiningTableService;
import com.df.order.entity.ServiceCard;
import com.df.order.exception.TableIsOccupiedException;
import com.df.order.service.contract.ServiceCardService;

public class DiningTableDistributorImpl implements DiningTableDistributor {

	@Autowired
	private ServiceCardService serviceCardService;

	@Autowired
	private DiningTableService diningTableService;

	public void setServiceCardService(ServiceCardService serviceCardService) {
		this.serviceCardService = serviceCardService;
	}

	public void setDiningTableService(DiningTableService diningTableService) {
		this.diningTableService = diningTableService;
	}

	@Override
	public synchronized ServiceCard acquireTables(String storeCode, List<String> tables) {
		List<String> avaliableTables = getAvaliableTables(storeCode);
		for (String table : tables) {
			if (!avaliableTables.contains(table)) {
				throw new TableIsOccupiedException(table);
			}
		}
		return serviceCardService.createServiceCard(storeCode, tables);
	}

	@Override
	public void releaseTables(String storeCode, long serviceCardId) {
		ServiceCard serviceCard = serviceCardService.getServiceCardById(storeCode, serviceCardId);
		if (serviceCard != null) {
			serviceCardService.deleteServiceCard(storeCode, serviceCardId);
		}
	}

	@Override
	public List<String> getAvaliableTables(String storeCode) {
		List<DiningTable> tables = diningTableService.getDiningTables(storeCode);
		List<String> occupiedTables = serviceCardService.getOccupiedTables(storeCode);
		ArrayList<String> avaliableTables = new ArrayList<String>();
		for (DiningTable table : tables) {
			if (!occupiedTables.contains(table.getCode())) {
				avaliableTables.add(table.getCode());
			}
		}
		return avaliableTables;
	}

	@Override
	public List<ServiceCard> getTableOccupation(String storeCode) {
		return serviceCardService.getAllServiceCard(storeCode);
	}
}
