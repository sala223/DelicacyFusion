package com.df.idm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RoleId id;

	@Column(length = 1025)
	private String description;

	@OneToMany
	@JoinFetch(value = JoinFetchType.OUTER)
	@JoinTable(name = "ROLE_PERMISSION")
	private List<Permission> permissions;

	Role() {
	}

	public Role(RoleId id) {
		this.id = id;
	}

	public RoleId getId() {
		return id;
	}

	void setId(RoleId id) {
		this.id = id;
	}

	public Role(String domain, String name) {
		this.id = new RoleId(domain, name);
	}

	@JsonIgnore
	public String getName() {
		return id.getName();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@JsonIgnore
	public String getDomain() {
		return id.getDomain();
	}

	@Override
	@JsonIgnore
	public String getAuthority() {
		return this.getName();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Role) {
			Role other = (Role) obj;
			if (this.getDomain().equals(other.getDomain())) {
				return this.getName().equals(other.getName());
			} else {
				return false;
			}
		}

		return false;
	}

	public int hashCode() {
		return (this.getDomain() + this.getName()).hashCode();
	}

	public String toString() {
		return this.getName();
	}
}
