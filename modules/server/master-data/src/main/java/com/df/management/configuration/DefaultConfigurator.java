package com.df.management.configuration;

import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.df.management.configuration.entity.GlobalConfiguration;

@Transactional
public class DefaultConfigurator implements Configurator {

	private static final Charset UTF8 = Charset.forName("utf-8");

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

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
		GlobalConfiguration global = this.fetchGlobalConfiguration(key, domain);
		if (global == null) {
			GlobalConfiguration gconf = new GlobalConfiguration();
			gconf.setDomain(domain);
			gconf.setKey(key);
			gconf.setValue(encodedValue.getBytes(UTF8));
			this.getEntityManager().persist(gconf);
		} else {
			global.setValue(encodedValue.getBytes(UTF8));
			this.getEntityManager().merge(global);
		}
	}

	protected GlobalConfiguration fetchGlobalConfiguration(String key, Domain domain) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<GlobalConfiguration> query = builder.createQuery(GlobalConfiguration.class);
		Root<GlobalConfiguration> root = query.from(GlobalConfiguration.class);
		Predicate keyEqual = builder.equal(root.get("key"), key);
		Predicate domainEqual = builder.equal(root.get("domain"), domain);
		query.where(keyEqual, domainEqual);
		TypedQuery<GlobalConfiguration> typeQuery = this.getEntityManager().createQuery(query);
		List<GlobalConfiguration> rs = typeQuery.getResultList();
		if (rs.size() > 0) {
			return rs.get(0);
		} else {
			return null;
		}
	}

	@Override
	public <T extends Configurable> T getConfigurable(Class<T> type, Domain domain, String key) {
		GlobalConfiguration conf = this.fetchGlobalConfiguration(key, domain);
		try {
			Constructor<T> defaultConstructor = type.getDeclaredConstructor();
			if (!defaultConstructor.isAccessible()) {
				defaultConstructor.setAccessible(true);
			}
			try {
				T instance = defaultConstructor.newInstance();
				if (conf != null) {
					instance.unmarshall(new String(conf.getValue(), UTF8));
				} else {
					instance.unmarshall(null);
				}
				return instance;
			} catch (Throwable ex) {
				throw new ConfigurationException(ex, ConfigurationException.CONFIGURABLE_INITIALIZATION_ERROR);
			}
		} catch (NoSuchMethodException ex) {
			throw new ConfigurationException(ex, ConfigurationException.CONFIGURABLE_NO_DEFAULT_CONSTRUCTOR);
		} catch (SecurityException ex) {
			throw new ConfigurationException(ex, ConfigurationException.CONFIGURABLE_NO_DEFAULT_CONSTRUCTOR);
		}
	}

}
