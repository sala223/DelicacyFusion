package com.df.crm.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.core.validation.exception.ValidationException;
import com.df.crm.dao.TenantDao;
import com.df.crm.entity.Tenant;
import com.df.crm.exception.TenantException;
import com.df.crm.service.contract.TenantService;
import com.df.idm.entity.RoleId;
import com.df.idm.entity.User;
import com.df.idm.service.contract.UserAuthorityService;
import com.df.idm.service.contract.UserManagementService;

@Transactional
public class TenantServiceImpl implements TenantService {

	@Autowired
	private TenantDao tenantDao;

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private UserAuthorityService userAuthorityService;

	@Autowired
	private Validator validator;

	public void setTenantDao(TenantDao tenantDao) {
		this.tenantDao = tenantDao;
	}

	public UserManagementService getUserManagementService() {
		return userManagementService;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setUserManagementService(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	public UserAuthorityService getUserAuthorityService() {
		return userAuthorityService;
	}

	public void setUserAuthorityService(UserAuthorityService userAuthorityService) {
		this.userAuthorityService = userAuthorityService;
	}

	@Override
	public Tenant createTenant(Tenant tenant, long ownerId) {
		Set<ConstraintViolation<Tenant>> violations = validator.validate(tenant);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		User owner = userManagementService.getUserById(ownerId);
		if (owner == null) {
			throw TenantException.tenantOwnerNotFound(ownerId);
		}
		List<Tenant> tenants = tenantDao.getOwnerTenants(ownerId);
		if (tenants.size() > 0) {
			throw TenantException.exceedTenantCreateLimitation();
		}
		tenant.setOwner(ownerId);
		Tenant newTenant = tenantDao.newTenant(tenant);
		userManagementService.addUserToTenant(owner.getId(), tenant.getCode());
		userAuthorityService.assign(owner, new RoleId(tenant.getCode(), RoleId.TENANT_ADMIN_NAME));
		return newTenant;
	}

	@Override
	public List<Tenant> getTenants(int firstResult, int maxResult) {
		return tenantDao.getTenants(firstResult, maxResult, false);
	}

	@Override
	public void deleteTenant(String tenantCode) {
		boolean isSuccess = tenantDao.disableTenant(tenantCode);
		if (!isSuccess) {
			throw TenantException.tenantWithCodeNotFound(tenantCode);
		}
	}

	@Override
	public Tenant findTenantByCode(String tenantCode) {
		return tenantDao.findTenantByCode(tenantCode);
	}

	@Override
	public Tenant updateTenant(Tenant tenant) {
		Set<ConstraintViolation<Tenant>> violations = validator.validate(tenant);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		Tenant found = tenantDao.findTenantByCode(tenant.getCode());
		if (found == null || !found.isEnabled()) {
			throw TenantException.tenantWithCodeNotFound(tenant.getCode());
		}
		if (!tenant.getName().equals(found.getName())) {
			if (tenantDao.findTenantByName(tenant.getName()) != null) {
				throw TenantException.tenantWithNameAlreadyExist(tenant.getName());
			}
		}
		found.setName(tenant.getName());
		found.setDescription(tenant.getDescription());
		found.setAddress(tenant.getAddress());
		tenantDao.update(found);
		return found;
	}

	@Override
	public Tenant getTenantByCode(String tenantCode) {
		Tenant found = tenantDao.findTenantByCode(tenantCode);
		if (found == null) {
			throw TenantException.tenantWithCodeNotFound(tenantCode);
		}
		return found;
	}

}
