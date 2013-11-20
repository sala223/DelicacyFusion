package com.df.order.service.contract;

import java.util.List;

import com.df.order.entity.Order;
import com.df.order.entity.ServiceCard;

public interface ServiceCardService {
	
	List<String> getOccupiedTables(String storeCode);

	List<ServiceCard> getAllServiceCard(String storeCode);

	boolean validateServiceCardId(String storeCode, long id, boolean throwException);

	ServiceCard getServiceCardById(String storeCode, long id);

	ServiceCard getServiceCardByOrderId(String storeCode, long orderId);

	ServiceCard getServiceCardByTable(String storeCode, String tableCode);

	ServiceCard createServiceCard(String storeCode, List<String> tableCodes);

	ServiceCard createServiceCard(String storeCode, String tableCode);

	void updateServiceCardTables(String storeCode, long serviceCardId, List<String> tableCodes);

	void deleteServiceCard(String storeCode, long serviceCardId);

	void associateServiceCardWithOrder(String storeCode, long serviceCardId, Order order);
}
