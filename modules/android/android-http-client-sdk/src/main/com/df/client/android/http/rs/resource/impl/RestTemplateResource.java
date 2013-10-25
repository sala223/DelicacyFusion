package com.df.client.android.http.rs.resource.impl;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.df.client.rs.resource.AbstractResource;
import com.google.gson.GsonBuilder;

public abstract class RestTemplateResource extends AbstractResource {

    private RestTemplate restTemplate;

    public RestTemplateResource() {
	restTemplate = new RestTemplate();
	restTemplate.setErrorHandler(new CustomResponseErrorHandler());
	GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss Z");
	gsonBuilder.serializeNulls();
	restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(gsonBuilder.create()));
    }

    public RestTemplate getRestTemplate() {
	return restTemplate;
    }
}
