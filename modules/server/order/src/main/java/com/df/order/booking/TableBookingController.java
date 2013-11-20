package com.df.order.booking;

import java.util.List;

import com.df.order.entity.ServiceCard;

public interface TableBookingController {

	ServiceCard acquireTables(String storeCode, List<String> tables);

	void releaseTables(String storeCode, long serviceCardId);
}
