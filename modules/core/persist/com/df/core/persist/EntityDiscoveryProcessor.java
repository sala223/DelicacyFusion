package com.df.core.persist;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

class EntityDiscoveryProcessor implements PersistenceUnitPostProcessor, ApplicationContextAware {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private static final Logger logger = LoggerFactory.getLogger(EntityDiscoveryProcessor.class);

    private ResourcePatternResolver resourcePatternResolver;

    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    private String resourcePattern = DEFAULT_RESOURCE_PATTERN;

    private String[] packages;

    private static final TypeFilter typeFilter = new AnnotationTypeFilter(Entity.class);

    private String packageToRelativedPath(String pkg) {
	return pkg.replace('.', '/');
    }

    public void setResourcePatternResolver(ResourcePatternResolver resourcePatternResolver) {
	this.resourcePatternResolver = resourcePatternResolver;
    }

    public void setResourcePattern(String resourcePattern) {
	this.resourcePattern = resourcePattern;
    }

    public void setPackages(String[] packages) {
	this.packages = packages;
    }

    public List<String> findEntityClasses(String... packages) {
	List<String> set = new LinkedList<String>();
	if (packages == null) {
	    return set;
	}
	for (String pkg : packages) {
	    String resourcePath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + packageToRelativedPath(pkg) + "/"
		    + this.resourcePattern;

	    Resource[] ress = null;
	    try {
		ress = this.resourcePatternResolver.getResources(resourcePath);
	    } catch (IOException ex) {
		logger.warn("Ignore @entity auto scan for resource path " + resourcePath, ex);
	    }
	    if (ress != null && ress.length > 0) {
		for (Resource res : ress) {
		    MetadataReader metadataReader;
		    try {
			metadataReader = metadataReaderFactory.getMetadataReader(res);
			if (isEntityClass(metadataReader)) {
			    String entityClass = metadataReader.getClassMetadata().getClassName();
			    if (logger.isDebugEnabled()) {
				logger.debug("Find entity class " + entityClass + " from resource " + res.getURL());
			    }
			    set.add(entityClass);
			}
		    } catch (IOException ex) {
			logger.warn("Read metadata error for resource " + res, ex);
		    }
		}

	    }
	}
	return set;
    }

    protected boolean isEntityClass(MetadataReader metadataReader) throws IOException {
	return typeFilter.match(metadataReader, this.metadataReaderFactory);
    }

    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
	if (pui != null) {
	    List<String> managedClass = pui.getManagedClassNames();
	    for (String entityClassName : findEntityClasses(packages)) {
		if (!(managedClass.contains(entityClassName))) {
		    managedClass.add(entityClassName);
		}
	    }
	}

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.resourcePatternResolver = applicationContext;
    }

}
