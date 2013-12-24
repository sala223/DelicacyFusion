package com.df.core.provision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProvisioningContext {

	private Map<Object, ProvisioningStatus> succeed = new ConcurrentHashMap<Object, ProvisioningStatus>();

	private Map<Object, ProvisioningStatus> failed = new ConcurrentHashMap<Object, ProvisioningStatus>();

	public ProvisioningContext() {
	}

	public synchronized void addBeanRunningStatus(Object beanId) {
		succeed.put(beanId, new ProvisioningStatus(beanId));
	}

	public synchronized void addBeanRunningStatus(Object beanId, Throwable ex) {
		failed.put(beanId, new ProvisioningStatus(beanId, ex));
	}

	public boolean hasError() {
		return failed.size() != 0;
	}

	public ProvisioningStatus getBeanRunningStatus(Object beanId) {
		ProvisioningStatus status = succeed.get(beanId);
		if (status == null) {
			status = failed.get(beanId);
		}
		return status;
	}

	public List<ProvisioningStatus> listBeansWithError() {
		List<ProvisioningStatus> beans = new ArrayList<ProvisioningStatus>();
		for (ProvisioningStatus ps : failed.values()) {
			beans.add(ps);
		}
		return beans;
	}

	public List<ProvisioningStatus> listBeansWithoutError() {
		List<ProvisioningStatus> beans = new ArrayList<ProvisioningStatus>();
		for (ProvisioningStatus ps : succeed.values()) {
			beans.add(ps);
		}
		return beans;
	}

	public static class ProvisioningStatus {

		private Object beanId;

		private Throwable error;

		private Class<? extends ProvisioningBean> beanClass;

		private boolean isSuccess = true;

		public ProvisioningStatus(Object beanId) {
			this.beanId = beanId;
		}

		public ProvisioningStatus(Object beanId, Throwable error) {
			this.beanId = beanId;
			this.error = error;
			isSuccess = false;
		}

		public Object getBeanId() {
			return beanId;
		}

		public Throwable getError() {
			return error;
		}

		public boolean isSuccess() {
			return isSuccess;
		}

		public void setBeanClass(Class<? extends ProvisioningBean> beanClass) {
			this.beanClass = beanClass;
		}

		public Class<? extends ProvisioningBean> getBeanClass() {
			return beanClass;
		}
	}
}
