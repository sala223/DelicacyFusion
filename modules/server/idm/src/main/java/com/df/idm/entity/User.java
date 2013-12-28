package com.df.idm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.FetchGroup;
import org.eclipse.persistence.annotations.FetchAttribute;
import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Cache
@Entity
@Table(name = "USERS")
@FetchGroup(name = "AuthenticationInfo", attributes = { @FetchAttribute(name = "password"),
        @FetchAttribute(name = "weiboAccount"), @FetchAttribute(name = "email"), @FetchAttribute(name = "cellPhone"), })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQUENCE")
	@SequenceGenerator(initialValue = 10000, name = "USER_ID_SEQUENCE", sequenceName = "USER_ID_SEQUENCE")
	@Column
	private long Id;

	@Column(length = 128)
	@Size(message = "{user.firstName.Size}", max = 128)
	private String firstName;

	@Column(length = 128)
	@Size(message = "{user.lastName.Size}", max = 128)
	private String lastName;

	@NotEmpty(message = "{user.password.NotEmpty}")
	@Size(message = "{user.password.Size}", min = 6, max = 30)
	@Column(length = 256, nullable = false)
	private String password;

	@Column(length = 128)
	@Size(message = "{user.nickName.Size}", max = 128)
	private String nickName;

	@Column
	private int age;

	@Column(length = 20)
	@Index(unique = true)
	@Size(message = "{user.cellPhone.Size}", max = 20)
	private String cellPhone;

	@Column(length = 512)
	private String imageId;

	@Column(length = 8)
	private Gender gender;

	@Column(length = 128, updatable = false, nullable = false)
	@Index(unique = true)
	@Email(message = "{user.email.Email}")
	@NotEmpty(message = "{user.email.NotEmpty}")
	@Size(message = "{user.email.Size}", max = 128)
	private String email;

	@Column(length = 128, unique = true, name = "WEIBO_ACCOUNT")
	@Index(unique = true)
	@Size(message = "{user.weiboAccount.Size}", max = 128)
	private String weiboAccount;

	@Column
	private boolean locked;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false)
	private Date createdTime;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "CHANGED_TIME")
	private Date changedTime;

	@Column(name = "IS_EMAIL_VERIFIED")
	private boolean isEmailVerified;

	@Column(name = "IS_CELL_PHONE_VERIFIED")
	private boolean isCellphoneVerified;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"))
	@JoinFetch(value = JoinFetchType.OUTER)
	private List<RoleId> roles = new ArrayList<RoleId>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_TENANT", joinColumns = @JoinColumn(name = "USER_ID"))
	@JoinFetch(value = JoinFetchType.OUTER)
	@Column(name = "TENANT_CODE")
	private List<String> tenants = new ArrayList<String>();

	public User() {
	}

	public User(String email, String password) {
		this.password = password;
		this.email = email;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getImage() {
		return imageId;
	}

	public void setImage(String image) {
		this.imageId = image;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getWeiboAccount() {
		return weiboAccount;
	}

	public void setWeiboAccount(String weiboAccount) {
		this.weiboAccount = weiboAccount;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getChangedTime() {
		return changedTime;
	}

	public void setChangedTime(Date changedTime) {
		this.changedTime = changedTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public boolean isCellphoneVerified() {
		return isCellphoneVerified;
	}

	public void setCellphoneVerified(boolean isCellphoneVerified) {
		this.isCellphoneVerified = isCellphoneVerified;
	}

	@PrePersist
	protected void fillDefaultValue() {
		this.setCreatedTime(new Date());
	}

	@PreUpdate
	protected void updateDefaultValue() {
		this.setChangedTime(new Date());
	}

	public static enum Gender {
		MALE, FEMALE
	}

	public User cleanCredential() {
		this.password = null;
		return this;
	}

	public void addRole(RoleId roleId) {
		if (!this.roles.contains(roleId)) {
			this.roles.add(roleId);
		}
	}

	public void removeRole(RoleId roleId) {
		this.roles.remove(roleId);
	}

	public List<RoleId> getRoles() {
		return this.roles;
	}

	public boolean isTenantUser() {
		return tenants.size() > 0;

	}

	public void addToTenantUser(String tenantCode) {
		if (!tenants.contains(tenantCode)) {
			tenants.add(tenantCode);
		}
	}

	public void removeFromTenantUser(String tenantCode) {
		tenants.remove(tenantCode);
	}

	public List<String> getTenants() {
		return tenants;
	}
}
