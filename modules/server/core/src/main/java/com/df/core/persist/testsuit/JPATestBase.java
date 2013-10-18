package com.df.core.persist.testsuit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	TenantContextInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:META-INF/datasource.xml" })
@ActiveProfiles(value = { "test" })
public abstract class JPATestBase {

}
