package com.df.idm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

@Embeddable
@Indexes({ @Index(columnNames = { "EXTERNAL_ID", "PROVIDER" }), @Index(columnNames = { "ACCESS_TOKEN", "PROVIDER" }) })
public class ExternalUserReference implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Provider {
		SINA
	}

	@Column(length = 128, unique = true, name = "EXTERNAL_ID")
	private String externalId;

	@Enumerated(EnumType.STRING)
	@Column(length = 32, unique = true, name = "PROVIDER")
	private Provider provider;

	ExternalUserReference() {
	}

	public ExternalUserReference(Provider provider, String externalId) {
		this.externalId = externalId;
		this.provider = provider;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "[externalId=" + externalId + ", provider=" + provider + "]";
	}

}
