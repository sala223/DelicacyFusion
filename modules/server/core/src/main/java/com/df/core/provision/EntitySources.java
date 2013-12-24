package com.df.core.provision;

import java.util.List;

public interface EntitySources {
	
	void open();

	void close();

	List<?> getEntitySet();

	boolean hasNext();

	List<EntityPostConstructor> getPostConstructors();

}
