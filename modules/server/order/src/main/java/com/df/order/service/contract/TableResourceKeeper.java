package com.df.order.service.contract;

import java.util.List;

import com.df.idm.entity.User;
import com.df.order.entity.TableResource;

public interface TableResourceKeeper {

	List<TableResource> getTables(String storeCode);

	void associateOrder(String storeCode, long orderId, String[] tableNumbers);

	TableResource[] requireTables(User user, String storeCode, String[] tableNumbers);

	void releaseTables(String storeCode, String[] tableNumbers);

}
