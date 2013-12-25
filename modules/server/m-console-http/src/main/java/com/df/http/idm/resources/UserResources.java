package com.df.http.idm.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.idm.entity.User;
import com.df.idm.service.contract.UserManagementService;

@Path("/user")
@Produces("application/json;charset=UTF-8")
@Component
public class UserResources {

	@Autowired
	private UserManagementService userManagementService;

	public void setUserManagementService(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	@POST
	@Path("/")
	public User newUser(User user) {
		return userManagementService.createUser(user).cleanCredential();
	}

	@PUT
	@Path("/")
	public User updateUser(User user) {
		return userManagementService.updateUser(user).cleanCredential();
	}

	@GET
	@Path("/{email}")
	public User getUserByEmail(@QueryParam("email") String email) {
		return userManagementService.getUserByEmail(email).cleanCredential();
	}
}
