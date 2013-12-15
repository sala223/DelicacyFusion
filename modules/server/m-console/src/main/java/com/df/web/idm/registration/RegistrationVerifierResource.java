package com.df.web.idm.registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.idm.service.contract.UserManagementService;

@Path("/verify")
@Produces("text/html")
@Component
public class RegistrationVerifierResource {

	@Autowired
	private UserManagementService userManagementService;

	@GET
	@Path("/")
	public void registrationVerify(@Context HttpServletResponse response, @Context HttpServletRequest request,
	        @QueryParam("token") String token) throws ServletException, IOException {
		boolean isVerified = userManagementService.verifyUserEmail(token);
		if (isVerified) {
			response.sendRedirect("verify_0.html");
		} else {
			response.sendRedirect("verify_1.html");
		}
	}
}
