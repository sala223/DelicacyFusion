package com.df.idm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoleId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 128, nullable = false)
	private String name;

	@Column(name = "DOMAIN", length = 64, nullable = false)
	private String domain;

	public static final String SYS_DOMIAN = "SYSTEM";

	public static final String TENANT_ADMIN_NAME = "TENANT_ADMIN";

	public static final RoleId TENANT_ADMIN = new RoleId(SYS_DOMIAN, TENANT_ADMIN_NAME);

	public static final String STORE_ADMIN_NAME = "STORE_ADMIN";

	public static final RoleId STORE_ADMIN = new RoleId(SYS_DOMIAN, STORE_ADMIN_NAME);

	public static final String STORE_SERVICE_NAME = "STORE_SERVICE";

	public static final RoleId STORE_SERVICE = new RoleId(SYS_DOMIAN, STORE_SERVICE_NAME);

	public static final String STORE_CASHIER_NAME = "STORE_CASHIER";

	public static final RoleId STORE_CASHIER = new RoleId(SYS_DOMIAN, STORE_CASHIER_NAME);

	RoleId() {
	}

	public RoleId(String domain, String roleName) {
		this.name = roleName;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		RoleId other = (RoleId) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoleId [name=" + name + ", domain=" + domain + "]";
	}

}
