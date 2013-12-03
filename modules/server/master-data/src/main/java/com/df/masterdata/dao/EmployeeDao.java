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
		Assert.notNull(email);
		email = StringUtils.normalizeEmail(email);
		Employee found = this.findEmployeeByEmail(email);
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
	
	public void updateEmployee(String storeCode, Employee employee){
		Employee found = this.findEmployeeByEmail(employee.getEmail());
		 
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

	public Employee findEmployeeByCellphone(String cellphone) {
		return this.findSingleEntityByProperty(Employee.class, EMPLOYEE.CELL_PHONE_PROPERTY, cellphone);
	}
}
