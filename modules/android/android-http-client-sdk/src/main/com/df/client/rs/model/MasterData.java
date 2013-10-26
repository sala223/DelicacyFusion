package com.df.client.rs.model;

import java.util.Date;

public abstract class MasterData {

    private String code;

    private Date createdTime;

    private Date changedTime;

    private long createdBy;

    private boolean isEnabled;

    public String getCode() {
	return code;
    }

    /**
     * Code is unique for each tenant
     */
    public void setCode(String code) {
	this.code = code;
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

    public long getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(long createdBy) {
	this.createdBy = createdBy;
    }

    public boolean isEnabled() {
	return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
	this.isEnabled = isEnabled;
    }
}
