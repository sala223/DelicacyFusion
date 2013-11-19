package com.df.management.configuration;

import java.nio.charset.Charset;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.df.management.configuration.entity.GlobalConfiguration;

@Transactional
public class DefaultConfigurator implements Configurator {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Charset UTF8 = Charset.forName("utf-8");

	@Override
	public void updateConfigurable(Configurable configurable) {
		String key = configurable.getConfigurationKey();
		Domain domain = configurable.getDomain();
		String encodedValue;
		try {
			encodedValue = configurable.marshall();
		} catch (Exception ex) {
			throw new ConfigurationException(ex, ConfigurationException.CONFIGURABLE_MARSHALL_ERROR);
		}
		GlobalConfiguration gconf = new GlobalConfiguration();
		gconf.setDomain(domain);
		gconf.setKey(key);
		gconf.setValue(encodedValue.getBytes(UTF8));
	}

	@Override
	public <T extends Configurable> T getConfigurable(Class<T> type, Domain domain, String key) {
		return null;
	}

}
