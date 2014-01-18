package com.df.masterdata.service.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.core.validation.exception.ValidationException;
import com.df.idm.entity.User;
import com.df.idm.service.contract.UserManagementService;
import com.df.masterdata.dao.EmployeeDao;
import com.df.masterdata.entity.Employee;
import com.df.masterdata.service.contract.EmployeeService;

@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private Validator validator;

	public void setUserManagementService(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public Employee newEmployee(Employee employee) {

		Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		Employee newEmployee = employeeDao.newEmployee(employee.getStoreCode(), employee);
		User found = userManagementService.getUserByEmail(employee.getEmail());
		if (found == null) {
			if (employee.getCellphone() != null) {
				found = userManagementService.getUserByCellPhone(employee.getCellphone());
			}

			if (found == null) {
				User user = new User();
				user.setFirstName(employee.getFirstName());
				user.setLastName(employee.getLastName());
				user.setEmail(employee.getEmail());
				user.setCellPhone(employee.getCellphone());

			}

		} else {

		}

		return newEmployee;
	}

	@Override
	public Employee upateEmployee(Employee employee) {
		employeeDao.updateEmployee(employee.getStoreCode(), employee);
		return null;
	}

	public void associateLoginUser() {

	}

}
