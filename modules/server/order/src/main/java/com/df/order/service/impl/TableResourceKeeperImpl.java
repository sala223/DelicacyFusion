package com.df.order.service.impl;

import java.util.List;

import com.df.idm.entity.User;
import com.df.order.entity.TableResource;
import com.df.order.service.contract.TableResourceKeeper;

public class TableResourceKeeperImpl implements TableResourceKeeper{

	@Override
    public List<TableResource> getTables(String storeCode) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public void associateOrder(String storeCode, long orderId, String[] tableNumbers) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public TableResource[] requireTables(User user, String storeCode, String[] tableNumbers) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public void releaseTables(String storeCode, String[] tableNumbers) {
	    // TODO Auto-generated method stub
	    
    }

	 

}
