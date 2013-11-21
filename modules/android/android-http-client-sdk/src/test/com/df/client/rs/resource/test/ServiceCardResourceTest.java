package com.df.client.rs.resource.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.df.client.rs.model.ServiceCard;
import com.df.client.rs.resource.ServiceCardResource;
import com.google.gson.Gson;

import android.test.suitebuilder.annotation.MediumTest;

public class ServiceCardResourceTest extends AbstractResourceTest {
	@MediumTest
	public void testGetTableOccupation() {
		ServiceCardResource serviceCardResource = this.getClient().getResource(ServiceCardResource.class);
		ServiceCard[] sc = serviceCardResource.getTableOccupation();
		Gson gson = new Gson();
		this.logInfo(gson.toJson(sc));
	}

	@MediumTest
	public void testGetAvaliableTables() {
		ServiceCardResource serviceCardResource = this.getClient().getResource(ServiceCardResource.class);
		String[] tables = serviceCardResource.getAvaliableTables();
		Gson gson = new Gson();
		this.logInfo(gson.toJson(tables));
	}

	@MediumTest
	public void testAcquireTablesAndReleaseTables() {
		ServiceCardResource serviceCardResource = this.getClient().getResource(ServiceCardResource.class);
		List<String> tables = new ArrayList<String>();
		tables.add("S1000009");
		ServiceCard sc = serviceCardResource.acquireTables(tables);
		serviceCardResource.releaseTables(sc.getId());
		String[] avaliableTables = serviceCardResource.getAvaliableTables();
		boolean isSuccess = false;
		for (String avaliableTable : avaliableTables) {
			if (avaliableTable.equals("S1000009")) {
				isSuccess = true;
				break;
			}
		}
		TestCase.assertTrue(isSuccess);
	}

}
