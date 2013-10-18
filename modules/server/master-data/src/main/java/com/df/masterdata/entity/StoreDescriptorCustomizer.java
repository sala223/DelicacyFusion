package com.df.masterdata.entity;


import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.ExpressionBuilder;

import com.df.core.persist.eclipselink.EmbeddedAttrNullableDescriptorCustomizer;

public class StoreDescriptorCustomizer extends EmbeddedAttrNullableDescriptorCustomizer {

    public StoreDescriptorCustomizer() {
	super("address");
    }

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
	super.customize(descriptor);
	ExpressionBuilder builder = new ExpressionBuilder();
	String table = descriptor.getTableName();
	org.eclipse.persistence.expressions.Expression left = builder.getTable(table).getField("id");
	org.eclipse.persistence.expressions.Expression right = builder.getTable("store_address").getField("store_id");
	descriptor.getDescriptorQueryManager().setMultipleTableJoinExpression(left.leftJoin(right, left.equal(right)));
    }
}
