package com.df.masterdata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.map.type.TypeFactory;

import com.df.masterdata.entity.Category;
import com.df.masterdata.exception.CategoryResourceBundleException;

public class JsonResourceBundleParser implements ResourceBundleParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonResourceBundleParser() {
	objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	objectMapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, true);
	objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    @Override
    public List<Category> parse(InputStream in) {
	ArrayType arrayType = TypeFactory.defaultInstance().constructArrayType(Category.class);
	Object value = null;
	try {
	    value = objectMapper.readValue(in, arrayType);
	} catch (Throwable ex) {
	    throw new CategoryResourceBundleException(ex);
	} finally {
	    try {
		in.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	if (value != null && value instanceof Category[]) {
	    Category[] cs = (Category[]) value;
	    return Arrays.asList(cs);
	}

	return new ArrayList<Category>();
    }
}
