package com.df.core.provision;

public abstract class AbstractProvisioningBean implements ProvisioningBean {

	private int order;

	public final void execute(ProvisioningContext context) {
		try {
			run(context);
			context.addBeanRunningStatus(this.getId());
		} catch (Throwable ex) {
			context.addBeanRunningStatus(this.getId(), ex);
		}
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public abstract void run(ProvisioningContext context) throws Exception;
}
