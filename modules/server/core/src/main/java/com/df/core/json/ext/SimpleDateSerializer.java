package com.df.core.json.ext;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class SimpleDateSerializer extends JsonSerializer<Date> {

    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    public static DateFormat getDateFormat() {
	return formatter;
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider paramSerializerProvider)
	    throws IOException, JsonProcessingException {
	gen.writeString(formatter.format(value));
    }
}