package com.df.masterdata.test;

import org.springframework.test.context.ContextConfiguration;

import com.df.core.persist.testsuit.JPATestBase;

@ContextConfiguration(locations = { "classpath:META-INF/master-data-beans.xml", "classpath:META-INF/core-beans.xml",
        "classpath:META-INF/blob-store-beans.xml", "classpath:META-INF/idm-beans.xml" })
public abstract class MasterDataJPABaseTest extends JPATestBase {
}
