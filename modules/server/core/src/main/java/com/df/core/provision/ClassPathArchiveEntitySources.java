package com.df.core.provision;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.df.core.common.utils.OrderedProperties;

public class ClassPathArchiveEntitySources extends JsonStreamEntitySources implements InitializingBean,
        ApplicationContextAware {

	private String manifestResourceName = "provisioning.properties";

	private ApplicationContext applicationContext;

	private static final String POST_CONSTRUCTOR_PREFIX = "post-constructor";

	private List<JsonStream> jsonStreams;

	private Iterator<JsonStream> iterator;

	private static final Logger logger = LoggerFactory.getLogger(ClassPathArchiveEntitySources.class);

	public ClassPathArchiveEntitySources(String manifestResourceName) {
		this.manifestResourceName = manifestResourceName;
	}

	public void parseManifest() {
		Resource manifestResource = applicationContext.getResource(manifestResourceName);
		if (manifestResource.exists()) {
			Properties properties = new OrderedProperties();
			try {
				jsonStreams = new ArrayList<JsonStream>();
				properties.load(manifestResource.getInputStream());
				List<EntityPostConstructor> postConstructors = new ArrayList<EntityPostConstructor>();
				Set<Object> keys = properties.keySet();
				for (Object object : keys) {
					String key = (String) object;
					String value = properties.getProperty(key);
					if (key.startsWith(POST_CONSTRUCTOR_PREFIX)) {
						if (applicationContext.containsBean(value)) {
							Object bean = applicationContext.getBean(value);
							if (bean instanceof EntityPostConstructor) {
								postConstructors.add((EntityPostConstructor) bean);
							} else {
								String fmt = "The post constructor bean {} is not an instance of {}";
								logger.warn(fmt, value, EntityPostConstructor.class.getName());
							}
						} else {
							String fmt = "The post constructor bean {} does not exist";
							logger.warn(fmt, value);
						}
					} else {
						Resource res = applicationContext.getResource(value);
						if (res.exists()) {
							logger.info("List provisioning json stream {}", value);
							jsonStreams.add(new JsonStream(res.getInputStream(), key));
						} else {
							logger.warn("non exist json stream {}", value);
						}
					}
				}
				this.setPostConstructors(postConstructors);
				iterator = jsonStreams.iterator();
			} catch (FileNotFoundException ex) {
				logger.warn(ex.getMessage(), ex);
			} catch (IOException ex) {
				logger.warn(ex.getMessage(), ex);
			}
		} else {
			logger.warn("Cannot read configuration file from {}, it does not exist?");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public void open() {
	}

	@Override
	public void close() {
	}

	@Override
	protected JsonStream nextJsonStream() {
		return iterator.next();
	}

	@Override
	public boolean hasNext() {
		if (iterator != null) {
			return iterator.hasNext();
		} else {
			return false;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.parseManifest();
	}

}
