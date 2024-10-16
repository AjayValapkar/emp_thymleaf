package com.example.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.models.Address;
import com.example.app.models.EmpDateTime;
import com.example.app.models.Employee;

@Service
public interface EmploeeService {

	String addNewEmp(Employee emp);

	List<Employee> fetchAllEmp();

	Employee getEmpById(Integer empId);

	String updateEmp(Employee emp, Integer empId);

	String deleteEmployee(Integer empId);

	Integer getNextAvailableId();

	String empPunch(EmpDateTime empdatetime, Integer empId);

	String empPunchOut(EmpDateTime empdatetime, Integer empId, Integer id);

	Integer getPunch(Integer empId);

	List<EmpDateTime> getEmpAtt(Integer empId);

	LocalDateTime getTodayPunch(Integer empId);

	String leaveDueNoPunch(Integer empId);
}
