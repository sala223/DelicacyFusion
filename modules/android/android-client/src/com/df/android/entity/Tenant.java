package com.df.android.entity;

public class Tenant {
	private String code;
	private String name;
	
	public Tenant(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
