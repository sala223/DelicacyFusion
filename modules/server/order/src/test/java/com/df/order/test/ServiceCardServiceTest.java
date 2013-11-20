package com.df.order.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.order.dao.ServiceCardDao;
import com.df.order.entity.ServiceCard;
import com.df.order.service.contract.ServiceCardService;

@Transactional
public class ServiceCardServiceTest extends OrderBaseTest {

	@Autowired
	private ServiceCardService serviceCardService;

	@Autowired
	private ServiceCardDao serviceCardDao;

	@Test
	public void testCreateServiceCard() {
		ServiceCard card = serviceCardService.createServiceCard("S1", "S1000001");
		TestCase.assertNotNull(card);
		TestCase.assertEquals(card.getTables().size(), 1);
	}

	@Test
	public void getServiceCardById() {
		ServiceCard card = serviceCardService.createServiceCard("S1", "S1000001");
		serviceCardDao.getEntityManager().flush();
		card = serviceCardService.getServiceCardById("S1", card.getId());
		TestCase.assertNotNull(card);
	}

	@Test
	public void getServiceCardByTable() {
		ServiceCard card = serviceCardService.createServiceCard("S1", "S1000001");
		card = serviceCardService.getServiceCardByTable("S1", "S1000001");
		TestCase.assertNotNull(card);
	}

}
