package com.df.masterdata.service.contract;

import com.df.masterdata.entity.Employee;

public interface EmployeeService {

	Employee createEmployee();

	void upateEmployee(Employee employee);
}
