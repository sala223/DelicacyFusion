package com.df.client.http;

public class LoginContext {

    private String userCode;

    private String password;

    public LoginContext(String userCode, String password) {
	this.userCode = userCode;
	this.password = password;
    }

    public String getUserCode() {
	return userCode;
    }

    public String getPassword() {
	return password;
    }

}
