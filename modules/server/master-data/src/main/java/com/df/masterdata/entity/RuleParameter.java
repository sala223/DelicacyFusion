package com.df.masterdata.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RuleParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "VALUE", length = 128, nullable = false)
    private String value;

    RuleParameter() {
    }

    public RuleParameter(String name, String value) {
	this.name = name;
	this.value = value;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }
}
