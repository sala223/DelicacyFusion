package com.df.order.test;

import org.springframework.test.context.ContextConfiguration;

import com.df.core.persist.testsuit.JPATestBase;

@ContextConfiguration(locations = { "classpath:META-INF/order-beans.xml", })
public class OrderBaseTest extends JPATestBase {

}