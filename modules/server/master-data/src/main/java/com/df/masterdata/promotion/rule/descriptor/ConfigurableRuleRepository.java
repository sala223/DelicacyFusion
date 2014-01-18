package com.df.masterdata.promotion.rule.descriptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.df.core.common.utils.json.JsonUtils;
import com.df.masterdata.exception.RuleRepositoryException;

public class ConfigurableRuleRepository extends DefaultRuleRepository implements InitializingBean, ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	private String resourceName = "classpath:META-INF/rules.json";

	private ObjectMapper objectMapper;

	private MessageSource messageSource;

	public ConfigurableRuleRepository() {
	}

	public ConfigurableRuleRepository(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	protected RuleDescriptor[] parseJsonStream(InputStream in) {
		try {
			DefaultRuleDescriptor[] rules = objectMapper.readValue(in, DefaultRuleDescriptor[].class);
			for (DefaultRuleDescriptor rule : rules) {
				this.registerRule(rule);
				if (messageSource != null) {
					Collection<RuleParameterDescriptor> parameters = rule.getParameters().values();
					for (RuleParameterDescriptor parameter : parameters) {
						String description = parameter.getDescription();
						if (description != null) {
							try {
								if (parameter instanceof DefaultRuleParameterDescriptor) {
									Locale locale = LocaleContextHolder.getLocale();
									String newDesc = messageSource.getMessage(description, new Object[0], locale);
									((DefaultRuleParameterDescriptor) parameter).setDescription(newDesc);
								}
							} catch (NoSuchMessageException ex) {
							}
						}
					}
				}
			}
			return rules;
		} catch (Throwable ex) {
			throw new RuleRepositoryException(ex, "Failed parse rule file from %s", resourceName);
		}
	}

	@Override
	public void afterPropertiesSet() {
		if (resourceLoader == null) {
			resourceLoader = new DefaultResourceLoader();
		}
		if (objectMapper == null) {
			objectMapper = JsonUtils.objectMapper;
		}
		Resource resource = this.resourceLoader.getResource(resourceName);
		InputStream in = null;
		try {
			in = resource.getInputStream();
		} catch (IOException ex) {
			throw new RuleRepositoryException(ex);
		}
		this.parseJsonStream(in);
	}

}
