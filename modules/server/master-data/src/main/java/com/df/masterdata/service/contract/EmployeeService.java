package com.df.masterdata.service.contract;

import com.df.masterdata.entity.Employee;

public interface EmployeeService {

	Employee newEmployee(Employee employee);

	Employee upateEmployee(Employee employee);
	
	void associateLoginUser();
}
