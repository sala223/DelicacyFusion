package com.df.client.http;

public class LoginContext {

    private String userCode;

    private String password;
    
    private String tenantCode;

    public LoginContext(String userCode, String password) {
	this.userCode = userCode;
	this.password = password;
    }
    
    public LoginContext(String tenantCode, String userCode, String password) {
	this.tenantCode = tenantCode;
  	this.userCode = userCode;
  	this.password = password;
      }

    public String getUserCode() {
	return userCode;
    }

    public String getPassword() {
	return password;
    }
    
    public String getTenantCode(){
	return tenantCode;
    }
    
    public void login() {

    }

    public void logout() {

    }
}
