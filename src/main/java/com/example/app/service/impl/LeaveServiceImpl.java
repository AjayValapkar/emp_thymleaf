package com.example.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.models.Employee;
import com.example.app.models.Leave;
import com.example.app.repository.EmployeeRepository;
import com.example.app.repository.LeaveRepository;
import com.example.app.service.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	LeaveRepository leaveRepo;

	@Autowired
	EmployeeRepository empRepo;

	@Override
	public String requestLeave(Leave leave, Integer empId) {
		// TODO Auto-generated method stub
		leave.setStatus(false);
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		if (emp == null) {
			return "Employee is Null";
		} else {
			try {
				LocalDate date = LocalDateTime.now().toLocalDate();
				if ((leave.getLeaveFrom() != null && date.isBefore(leave.getLeaveFrom())
						&& (leave.getLeaveTo() != null && leave.getLeaveFrom().isBefore(leave.getLeaveTo())))
						|| (leave.getSingleDay() != null && date.isBefore(leave.getSingleDay()))) {
					if ((leave.getLeaveFrom() != null && leave.getLeaveTo() != null && leave.getSingleDay() == null)
							|| (leave.getLeaveFrom() == null && leave.getLeaveTo() == null
									&& leave.getSingleDay() != null)) {
						emp.getLeaves().add(leave);
						leave.setEmployee(emp);
						leaveRepo.save(leave);
						return "Successfully Applied";
					}
					return "You cannot apply on this date";
//					
				} else {
					return "You cannot apply on this date";
				}

			} catch (NullPointerException e) {
				return e.getMessage();
			}

		}

	}

	@Override
	public String responseLeave(Integer id, Boolean status) {
		// TODO Auto-generated method stub
		Leave leave = leaveRepo.findById(id).orElseThrow(() -> new RuntimeException("Leave Not found"));
		leave.setStatus(status);
		leaveRepo.save(leave);
		return "succefull";
	}

	@Override
	public List<Leave> allLeave(Integer empId) {
		// TODO Auto-generated method stub
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		List<Leave> leave = emp.getLeaves();

		return emp.getLeaves();
	}
	

}
