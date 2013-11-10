package com.df.client.rs.model;

public class Category extends MasterData {

    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
    	return (o instanceof Category) && (o != null) && ((Category)o).getCode().equals(getCode());
    }
}
