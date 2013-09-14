package com.df.order.test;

import javax.inject.Inject;

import org.junit.Test;

import com.df.order.dao.OrderDao;

public class OrderDALTest extends OrderBaseTest {

    @Inject
    private OrderDao orderDAL;

    @Test
    public void testCreateOrder() {

    }
}
