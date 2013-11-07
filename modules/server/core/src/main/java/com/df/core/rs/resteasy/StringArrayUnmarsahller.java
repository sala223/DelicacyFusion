package com.df.core.rs.resteasy;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;
import org.jboss.resteasy.spi.StringParameterUnmarshaller;

public class StringArrayUnmarsahller implements StringParameterUnmarshaller<String[]> {

    @Retention(RetentionPolicy.RUNTIME)
    @StringParameterUnmarshallerBinder(StringArrayUnmarsahller.class)
    public static @interface StringArray {
    }

    @Override
    public String[] fromString(String params) {
	if (params == null) {
	    return new String[0];
	}
	return params.split(",");
    }

    @Override
    public void setAnnotations(Annotation[] annotations) {
    }

}
