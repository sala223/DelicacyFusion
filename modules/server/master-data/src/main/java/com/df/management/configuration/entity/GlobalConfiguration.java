package com.df.management.configuration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;
import com.df.management.configuration.Domain;

@XmlRootElement
@Entity
@Index(name = "IDX_GLOBAL_CONFIGURATION_T_DOMAIN_KEY", unique = true, columnNames = { "DOMAIN", "KEY",
        MultiTenantSupport.TENANT_COLUMN })
@Table(name = "GLOBAL_CONFIGURATION")
public class GlobalConfiguration extends MultiTenantSupport {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	@JsonIgnore
	@XmlTransient
	@Index
	private long id;

	@Column(name = "KEY", length = 128, nullable = false)
	private String Key;

	@Column(name = "domian", length = 128, nullable = false)
	private Domain domain;

	@Column(name = "VALUE", length = 128, nullable = false)
	private byte[] value;

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}
