package com.df.masterdata.auxiliary;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.df.core.common.utils.LocalCache;
import com.df.masterdata.exception.CategoryResourceBundleException;
import com.df.masterdata.service.impl.ResourceBundleParser;

public class ResourceBundleCategoryLoader implements CategoryLoader {

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	private static LocalCache<Locale, Map<String, Category>> cache = new LocalCache<Locale, Map<String, Category>>();

	private String resourceBaseName = "META-INF/category";

	private String resourceBaseSuffix;

	private ResourceBundleParser resourceBundleParser;

	public ResourceBundleCategoryLoader(ResourceBundleParser resourceBundleParser) {
		this.resourceBundleParser = resourceBundleParser;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void setResourceBaseName(String resourceBaseName) {
		this.resourceBaseName = resourceBaseName;
	}

	public void setResourceBaseSuffix(String resourceBaseSuffix) {
		this.resourceBaseSuffix = resourceBaseSuffix;
	}

	public void setResourceBundleParser(ResourceBundleParser resourceBundleParser) {
		this.resourceBundleParser = resourceBundleParser;
	}

	@Override
	public Category[] loadCategories() {
		Locale locale = LocaleContextHolder.getLocale();
		return getCategories(locale);
	}

	public Category[] getCategories(Locale locale) {
		Map<String, Category> cmap = cache.get(locale);
		if (cmap == null) {
			synchronized (ResourceBundleCategoryLoader.class) {
				if (cmap == null) {
					InputStream stream = this.loadResourceBundle(resourceBaseName, resourceBaseSuffix, locale);
					cmap = parseResourceBundle(stream);
					cache.put(locale, cmap);
				}
			}
		}

		return cmap.values().toArray(new Category[0]);
	}

	protected Map<String, Category> parseResourceBundle(InputStream resourceBundle) {
		return resourceBundleParser.parse(resourceBundle);
	}

	protected InputStream loadResourceBundle(String baseName, String suffix, Locale locale) {
		String bundleName = toBundleName(baseName, suffix, locale);
		Resource resource = resourceLoader.getResource(bundleName);
		if (resource.exists()) {
			try {
				return resource.getInputStream();
			} catch (IOException e) {
				throw new CategoryResourceBundleException(e, "Failed loading resource bundle %s", bundleName);
			}
		} else {
			if (locale != Locale.ROOT) {
				return loadResourceBundle(baseName, suffix, Locale.ROOT);
			} else {
				throw new CategoryResourceBundleException(null, "Cannot load resource bundle %s", bundleName);
			}
		}
	}

	protected String toBundleName(String baseName, String suffix, Locale locale) {
		if (locale == Locale.ROOT) {
			if (suffix != null) {
				return baseName + "." + suffix;
			} else {
				return baseName;
			}
		}

		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();

		if (language == "" && country == "" && variant == "") {
			return baseName;
		}

		StringBuilder sb = new StringBuilder(baseName);
		sb.append('_');
		if (variant != "") {
			sb.append(language).append('_').append(country).append('_').append(variant);
		} else if (country != "") {
			sb.append(language).append('_').append(country);
		} else {
			sb.append(language);
		}
		if (suffix != null) {
			return sb.toString() + "." + suffix;
		} else {
			return sb.toString();
		}

	}

	public Category getCategory(String categoryCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return getCategory(categoryCode, locale);
	}

	public Category getCategory(String categoryCode, Locale locale) {
		Map<String, Category> cmap = cache.get(locale);
		if (cmap == null) {
			synchronized (ResourceBundleCategoryLoader.class) {
				if (cmap == null) {
					InputStream stream = this.loadResourceBundle(resourceBaseName, resourceBaseSuffix, locale);
					cmap = parseResourceBundle(stream);
					cache.put(locale, cmap);
				}
			}
		}
		return cmap.get(categoryCode);
	}
}
