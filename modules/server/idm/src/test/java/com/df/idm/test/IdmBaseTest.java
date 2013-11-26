package com.df.idm.test;

import org.springframework.test.context.ContextConfiguration;

import com.df.core.persist.testsuit.JPATestBase;

@ContextConfiguration(locations = { "classpath:META-INF/idm-beans.xml" })
public abstract class IdmBaseTest extends JPATestBase {

}
