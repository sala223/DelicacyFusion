package com.df.masterdata.entity;

import java.io.Serializable;

public class StoreObjectId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String storeCode;

	private String code;

	public StoreObjectId() {
	}

	public StoreObjectId(String storeCode, String code) {
		this.storeCode = storeCode;
		this.code = code;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((storeCode == null) ? 0 : storeCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof StoreObjectId))
			return false;
		StoreObjectId other = (StoreObjectId) obj;
		return other.storeCode.equals(this.storeCode) && other.code.equals(this.code);
	}

}
