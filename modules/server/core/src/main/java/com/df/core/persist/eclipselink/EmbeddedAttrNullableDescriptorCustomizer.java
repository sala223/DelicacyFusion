package com.df.core.persist.eclipselink;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;

public class EmbeddedAttrNullableDescriptorCustomizer implements DescriptorCustomizer {

    private String[] nullableAttrs;

    public EmbeddedAttrNullableDescriptorCustomizer(String... nullableAttrs) {
	this.nullableAttrs = nullableAttrs;
    }

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
	if (nullableAttrs != null)
	    for (String nullableAttr : nullableAttrs) {
		((AggregateObjectMapping) descriptor.getMappingForAttributeName(nullableAttr)).allowNull();
	    }
    }

}
