package com.df.core.persist.eclipselink;

public class Property<T> {

    private String name;

    private Object value;

    public Property(String name, Object value) {
	this.name = name;
	this.value = value;
    }

    public String getName() {
	return name;
    }

    public Object getValue() {
	return value;
    }
}
