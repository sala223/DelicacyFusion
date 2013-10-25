package com.df.client.android.http.rs.resource.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.df.client.rs.resource.exception.ResourceProcessException;
import com.google.gson.Gson;

public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
	if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
	    InputStream body = response.getBody();
	    Gson gson = new Gson();
	    ErrorEntity ee = null;
	    try {
		ee = gson.fromJson(new InputStreamReader(body), ErrorEntity.class);
	    } catch (Throwable ex) {
		super.handleError(response);
	    }
	    if (ee != null) {
		throw new ResourceProcessException(ee.getRealm(), ee.getErrorCode(), ee.getError());
	    }
	}
	super.handleError(response);
    }

    static class ErrorEntity {
	private String realm;

	private int errorCode;

	private String error;

	public ErrorEntity(String realm, int errorCode, String error) {
	    this.realm = realm;
	    this.errorCode = errorCode;
	    this.error = error;
	}

	public String getRealm() {
	    return realm;
	}

	public void setRealm(String realm) {
	    this.realm = realm;
	}

	public int getErrorCode() {
	    return errorCode;
	}

	public void setErrorCode(int errorCode) {
	    this.errorCode = errorCode;
	}

	public String getError() {
	    return error;
	}

	public void setError(String error) {
	    this.error = error;
	}
    }
}
