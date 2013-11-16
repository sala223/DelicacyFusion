package com.df.masterdata.auxiliary.template;

import java.io.Serializable;

public class CategoryProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    CategoryProfile() {
    }

    public CategoryProfile(String code, String name) {
	this.code = code;
	this.name = name;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
