package com.df.management.configuration;

public interface Configurator {

	void updateConfigurable(Configurable configurable);

	<T extends Configurable> T getConfigurable(Class<T> type, Domain domain, String key);
}