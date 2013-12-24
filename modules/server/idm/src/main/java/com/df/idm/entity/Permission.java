package com.df.idm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSIONS")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 128)
	private String name;

	@Column(length = 1025)
	private String description;

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
