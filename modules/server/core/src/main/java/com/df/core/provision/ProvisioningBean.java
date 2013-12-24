package com.df.core.provision;

import org.springframework.core.Ordered;

public interface ProvisioningBean extends Ordered {

	void execute(ProvisioningContext context);

	Object getId();
}
