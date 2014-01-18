package com.df.masterdata.dao;

import org.springframework.util.Assert;

import com.df.core.common.utils.StringUtils;
import com.df.masterdata.entity.Constants.EMPLOYEE;
import com.df.masterdata.entity.Employee;
import com.df.masterdata.exception.EmployeeException;

public class EmployeeDao extends MasterDataAccessFoundation {

	public Employee newEmployee(String storeCode, Employee employee) {
		employee.setStoreCode(storeCode);
		String email = employee.getEmail();
		email = StringUtils.normalizeEmail(email);
		Assert.notNull(email);
		Employee found = this.findEmployeeByCode(employee.getCode());
		if (found != null) {
			throw EmployeeException.codeIsOccupied(employee.getCode());
		}
		found = this.findEmployeeByEmail(email);
		if (found != null) {
			throw EmployeeException.emailIsOccupied(email);
		}
		if (!StringUtils.isEmpty(employee.getEmail())) {
			found = this.findEmployeeByCellphone(employee.getCellphone());
			if (found != null) {
				throw EmployeeException.cellphoneIsOccupied(employee.getCellphone());
			}
		}
		employee.setEmail(email);
		this.insert(employee);
		return employee;
	}

	public Employee updateEmployee(String storeCode, Employee employee) {
		Employee found = this.findEmployeeByCode(employee.getCode());
		if (found == null) {
			throw EmployeeException.employeeWithCodeNotFound(employee.getCode());
		}
		found.setBirth(employee.getBirth());
		found.setFirstName(employee.getFirstName());
		found.setGender(employee.getGender());
		found.setLastName(employee.getLastName());
		this.update(found);
		return found;
	}

	public void updateEmployeeEmail(Employee employee, String newEmail) {
		Assert.notNull(newEmail);
		newEmail = StringUtils.normalizeEmail(newEmail);
		Employee found = this.findEmployeeByEmail(newEmail);
		if (found != null) {
			throw EmployeeException.emailIsOccupied(employee.getEmail());
		}
		employee.setEmail(newEmail);
		this.update(employee);
	}

	public void updateEmployeeCellphone(Employee employee, String newCellPhone) {
		Assert.notNull(newCellPhone);
		Employee found = this.findEmployeeByCellphone(newCellPhone);
		if (found != null) {
			throw EmployeeException.emailIsOccupied(employee.getEmail());
		}
		employee.setCellphone(newCellPhone);
		this.update(employee);
	}

	public Employee findEmployeeByEmail(String email) {
		return this.findSingleEntityByProperty(Employee.class, EMPLOYEE.EMAIL_PROPERTY, email);
	}

	public Employee findEmployeeByCode(String code) {
		return this.findSingleEntityByProperty(Employee.class, EMPLOYEE.CODE_PROPERTY, code);
	}

	public Employee findEmployeeByCellphone(String cellphone) {
		return this.findSingleEntityByProperty(Employee.class, EMPLOYEE.CELL_PHONE_PROPERTY, cellphone);
	}
}
