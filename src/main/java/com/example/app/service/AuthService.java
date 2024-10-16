package com.example.app.service;

import org.springframework.stereotype.Service;

import com.example.app.dto.LoginDto;
import com.example.app.models.Employee;

@Service
public interface AuthService {

	Employee loginEmployee(LoginDto login);
}
