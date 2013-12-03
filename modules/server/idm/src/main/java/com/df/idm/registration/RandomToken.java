package com.df.idm.registration;

import java.io.Serializable;
import java.util.Date;

public class RandomToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;

	private Date validFrom;

	private Date validTo;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
}
