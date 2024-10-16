package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.models.Leave;

@Service
public interface LeaveService {
	
	String requestLeave(Leave leave, Integer empId);
	
	String responseLeave(Integer id, Boolean status);
	
	List<Leave> allLeave(Integer empId);
	
	

}
