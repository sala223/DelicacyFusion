package com.df.masterdata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
public class Employee extends MasterData {

	public static enum Gender {
		MALE, FEMALE
	}

	@Column(length = 64, name = "FIRST_NAME")
	private String firstName;

	@Column(length = 64, name = "LAST_NAME")
	private String lastName;

	@Column(length = 128, nullable = false, name = "EMAIL")
	private String email;

	@Column(length = 20, name = "CELL_PHONE")
	private String cellphone;

	@Column(length = 255, name = "STORE_CODE")
	private String storeCode;

	@Column(length = 16, name = "GENDER")
	private Gender gender;

	@Temporal(value = TemporalType.DATE)
	@Column(length = 16, name = "BIRTH")
	private Date birth;

	@Column(name = "IS_LICENSED")
	private boolean isLicensed;

	@Column(name = "Login_USER_ID")
	private long loginUserId;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public boolean isLicensed() {
		return isLicensed;
	}

	public void setLicensed(boolean isLicensed) {
		this.isLicensed = isLicensed;
	}

	public long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
}
