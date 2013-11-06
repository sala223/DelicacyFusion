package com.df.masterdata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.type.TypeReference;

import com.df.masterdata.auxiliary.template.CategoryProfile;
import com.df.masterdata.exception.CategoryResourceBundleException;

public class JsonResourceBundleParser implements ResourceBundleParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonResourceBundleParser() {
	objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	objectMapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, true);
	objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    @Override
    public Map<String, CategoryProfile> parse(InputStream in) {
	Map<String, CategoryProfile> value = new HashMap<String, CategoryProfile>();
	try {
	    value = objectMapper.readValue(in, new TypeReference<HashMap<String, CategoryProfile>>() {
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
