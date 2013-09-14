package com.df.masterdata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.df.masterdata.entity.Category;
import com.df.masterdata.exception.CategoryResourceBundleException;
import com.df.masterdata.service.contract.CategoryServiceInf;

public class ResourceBundleCategoryService implements CategoryServiceInf {

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    private String resourceBaseName = "META-INF/category_template";

    private String resourceBaseSuffix;

    private ResourceBundleParser resourceBundleParser;

    public ResourceBundleCategoryService(ResourceBundleParser resourceBundleParser) {
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
    public List<Category> getCategories() {
	Locale locale = LocaleContextHolder.getLocale();
	InputStream stream = this.loadResourceBundle(resourceBaseName, resourceBaseSuffix, locale);
	return parseResourceBundle(stream);
    }

    protected List<Category> parseResourceBundle(InputStream resourceBundle) {
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

    @Override
    public void newCategory(Category category) {
	throw new UnsupportedOperationException();

    }

    @Override
    public void removeCategory(String categoryCode) {
	throw new UnsupportedOperationException();
    }
}
