package com.example.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.example.app.dto.HoildayDto;
import com.example.app.models.Holiday;
import com.example.app.repository.HolidayRepository;
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

	@Autowired
	HolidayRepository holidayRepository;

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

	@Override
	public String newHoliday(Holiday holiday) {
		try{
			holidayRepository.save(holiday);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "Succefully added new Holiday";
	}

	@Override
	public List<HoildayDto> getAllHoliday() {
		List<HoildayDto> holiday = new ArrayList<>();
		List<Holiday> vacation = holidayRepository.findAll();
		for(Holiday holi: vacation){
			HoildayDto newDto = new HoildayDto(holi.getId(), Month.of(holi.getDate().getMonthValue()),holi.getHolidayTitle(),holi.getDate());
			holiday.add(newDto);
		}
		return holiday;
	}


}
