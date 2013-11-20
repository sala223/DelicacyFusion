package com.df.management.configuration;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.df.core.json.ext.SimpleDateSerializer;

public abstract class JsonObjectConfigurable implements Configurable {

	private static ObjectMapper objectMapper;

	protected synchronized static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(SimpleDateSerializer.getDateFormat());
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		return objectMapper;
	}

	@Override
	public String marshall() throws JsonGenerationException, JsonMappingException, IOException {
		return getObjectMapper().writeValueAsString(this);
	}

	@Override
	public void unmarshall(String encoded) throws Exception {
		if (encoded == null) {
			this.initWithDefault();
		} else {
			mappingProperties(this.parse(encoded));
		}
	}

	protected Object parse(String encoded) throws JsonParseException, JsonMappingException, IOException {
		return getObjectMapper().readValue(encoded, this.getClass());
	}

	protected abstract void mappingProperties(Object value);

	protected abstract void initWithDefault();
}
