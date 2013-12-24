package com.df.core.provision;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.util.ClassUtils;

import com.df.core.common.utils.json.JsonUtils;

public abstract class JsonStreamEntitySources extends AbstractEntitySources {

	protected abstract JsonStream nextJsonStream();

	protected List<?> readJsonStream(JsonStream stream) {
		try {
			if (stream.getInputStream() == null) {
				throw new DataProvisioningException("json input stream must not be null");
			}
			Class<?> entityClass = ClassUtils.forName(stream.getEntityClassName(), ClassUtils.getDefaultClassLoader());
			Object object = JsonUtils.readObject(stream.getInputStream(), entityClass);
			if (object.getClass().isArray()) {
				Object[] objs = (Object[]) object;
				return Arrays.asList(objs);
			} else if (object instanceof List<?>) {
				return (List<?>) object;
			} else if (object instanceof Collection<?>) {
				ArrayList<Object> list = new ArrayList<Object>();
				list.addAll((Collection<?>) object);
				return list;
			} else {
				return Arrays.asList(object);
			}
		} catch (ClassNotFoundException ex) {
			String format = "Cannot find entity class %s for mapping";
			throw new DataProvisioningException(ex, format, stream.getEntityClassName());
		}
	}

	@Override
	public List<?> getEntitySet() {
		JsonStream stream = this.nextJsonStream();
		return this.readJsonStream(stream);
	}

	public static class JsonStream {

		private InputStream in;

		private String entityClassName;

		public JsonStream(InputStream in, String entityClassName) {
			this.in = in;
			this.entityClassName = entityClassName;
		}

		public InputStream getInputStream() {
			return in;
		}

		public String getEntityClassName() {
			return entityClassName;
		}

		public void setEntityClassName(String entityClassName) {
			this.entityClassName = entityClassName;
		}

		@Override
		public String toString() {
			return "JsonStream [in=" + in + ", entityClassName=" + entityClassName + "]";
		}
	}
}
