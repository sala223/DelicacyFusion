package com.df.idm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "PERMISSIONS")
public class Permission implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private long id;

	@Column(length = 256)
	private String name;

	@Column(length = 1025)
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getAuthority() {
		return name;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Permission) {
			Permission other = (Permission) obj;
			return this.getName().equals(other.getName());
		}

		return false;
	}

	public int hashCode() {
		return this.getName().hashCode();
	}

	public String toString() {
		return "Permission [name=" + this.getName() + "]";
	}
}
