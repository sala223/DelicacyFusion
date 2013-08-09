package com.df.core.web.jaxrs;


import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;

import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.JAXRSServiceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class JAXRSServicePublisherBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = ClassHelper.getRealClass(bean);
		if (clazz.isAnnotationPresent(Path.class)) {
			createAndPublishRSService(bean);
		}
		return bean;
	}

	private void createAndPublishRSService(Object bean) {
		JAXRSServerFactoryBean serverBean = applicationContext.getBean(JAXRSServerFactoryBean.class);
		if (serverBean == null) {
			throw new NullPointerException("JAXRSServerFactoryBean bean is not defined");
		}
		JAXRSServiceFactoryBean serviceFactory = serverBean.getServiceFactory();
		List<Object> beans = new ArrayList<Object>();
		beans.add(bean);
		serviceFactory.setResourceClassesFromBeans(beans);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
