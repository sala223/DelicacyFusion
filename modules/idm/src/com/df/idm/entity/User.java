package com.df.idm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.FetchGroup;
import org.eclipse.persistence.annotations.FetchAttribute;
import org.eclipse.persistence.annotations.Index;

@Cache
@Entity
@FetchGroup(name = "AuthenticationInfo", attributes = { @FetchAttribute(name = "password"),
		@FetchAttribute(name = "weiboAccount"), @FetchAttribute(name = "email"), @FetchAttribute(name = "telephone"), })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private long Id;

	@Column(length = 32)
	private String firstName;

	@Column(length = 56)
	private String lastName;

	@Column(length = 256)
	private String password;

	@Column(length = 128)
	private String nickName;

	@Column
	private int age;

	@Column(length = 20)
	@Index(unique = true)
	private String telephone;

	@Column(length = 512)
	private String imageId;

	@Column(length = 20)
	private String gender;

	@Column(length = 128)
	@Index(unique = true)
	private String email;

	@Column(unique = true)
	private String weiboAccount;

	@Column
	private boolean locked;

	@Temporal(value = TemporalType.DATE)
	@Column(nullable = false)
	private Date createdTime;

	@Temporal(value = TemporalType.DATE)
	@Column
	private Date changedTime;

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
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

	@PrePersist
	protected void fillDefaultValue() {
		if (this.createdTime == null) {
			this.setCreatedTime(new Date());
		}
	}
}
