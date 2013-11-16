package com.df.masterdata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.type.TypeReference;

import com.df.masterdata.auxiliary.Category;
import com.df.masterdata.exception.CategoryResourceBundleException;

public class JsonResourceBundleParser implements ResourceBundleParser {

	private ObjectMapper objectMapper = new ObjectMapper();

	public JsonResourceBundleParser() {
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, true);
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
	}

	@Override
	public Map<String, Category> parse(InputStream in) {
		Map<String, Category> value = new HashMap<String, Category>();
		try {
			value = objectMapper.readValue(in, new TypeReference<HashMap<String, Category>>() {
			});
		} catch (Throwable ex) {
			throw new CategoryResourceBundleException(ex);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
}
