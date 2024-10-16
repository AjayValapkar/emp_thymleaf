package com.example.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.app.dto.LoginDto;
import com.example.app.models.Employee;
import com.example.app.repository.EmployeeRepository;
import com.example.app.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	EmployeeRepository empRepo;

	@Override
	public Employee loginEmployee(LoginDto login) {
		// TODO Auto-generated method stub
		Employee employee = empRepo.findByEmpEmail(login.getEmail());
		if (employee == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usernot found");
		} else {
			if (!employee.getPassword().equals(login.getPassword())) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password is incorrect");
			}
		}
		return employee;
	}

}
