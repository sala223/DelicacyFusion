package com.df.http.crm.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.df.core.common.utils.StringUtils;
import com.df.crm.entity.Tenant;
import com.df.crm.service.contract.TenantService;
import com.df.idm.authentication.UserContext;
import com.df.idm.authentication.UserContextHelper;
import com.df.idm.entity.User;
import com.df.idm.service.contract.UserManagementService;

@Path("/tenant")
@Produces("application/json;charset=UTF-8")
@Component
public class TenantResource {

	@Autowired
	private TenantService tenantService;

	@Autowired
	private UserManagementService userManagementService;

	@POST
	@Path("/sign")
	public Tenant sign(TenantRegistration registration) {
		String account = registration.getAdminId();
		String password = registration.getPassword();
		User user = new User();
		if (StringUtils.isValidEmail(account)) {
			user.setEmail(account);
		} else {
			user.setCellPhone(account);
		}
		if (StringUtils.isEmpty(password)) {
			user.setPassword(StringUtils.randomString(10));
		} else {
			user.setPassword(password);
		}
		User newUser = userManagementService.createUser(user);
		Tenant tenant = new Tenant();
		tenant.setCode(registration.getTenantCode());
		tenant.setAddress(registration.getAddress());
		tenant.setName(registration.getTenantName());
		return tenantService.createTenant(tenant, newUser.getId());
	}

	@POST
	@Path("/")
	public Tenant createTenant(Tenant tenant) {
		UserContext user = UserContextHelper.getCurrentUser();
		tenantService.createTenant(tenant, user.getUserId());
		User found = userManagementService.getUserById(user.getUserId());
		UserContextHelper.updateUserContext(found);
		return tenant;
	}

	@PUT
	@Path("/")
	@PreAuthorize("hasTenantRole(#tenant.code,'TENANT_ADMIN')")
	public Tenant updateTenant(Tenant tenant) {
		tenantService.updateTenant(tenant);
		return tenant;
	}

	@GET
	@Path("/")
	public List<Tenant> getTenants(@QueryParam("fromResult") int firstResult, @QueryParam("maxResult") int maxResult) {
		return tenantService.getTenants(firstResult, maxResult);
	}

	@GET
	@Path("/{tenantCode}")
	public Tenant getTenantByCode(@PathParam("tenantCode") String tenantCode) {
		return tenantService.getTenantByCode(tenantCode);
	}
}
