package com.df.android.entity;

public class ItemCategory {
	private String code;
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
    	return (o instanceof ItemCategory) && (o != null) && ((ItemCategory)o).getCode().equals(getCode());
    }
}
