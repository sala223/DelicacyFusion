package com.df.management.configuration;

public interface Configurable {

	Domain getDomain();

	String getConfigurationKey();

	String marshall() throws Exception;

	void unmarshall(String encoded) throws Exception;
}
