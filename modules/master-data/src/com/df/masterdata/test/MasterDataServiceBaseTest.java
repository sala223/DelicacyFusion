package com.df.masterdata.test;

import org.springframework.test.context.ContextConfiguration;

import com.df.core.persist.testsuit.JPATestBase;

@ContextConfiguration(locations = { "classpath:META-INF/master-data-beans.xml" })
public class MasterDataServiceBaseTest extends JPATestBase{
}
