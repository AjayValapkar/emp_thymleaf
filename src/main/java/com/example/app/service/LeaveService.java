package com.example.app.service;

import java.time.Month;
import java.time.Year;
import java.util.List;

import com.example.app.dto.EmpAttendace;
import com.example.app.dto.HoildayDto;
import com.example.app.models.Holiday;
import org.springframework.stereotype.Service;

import com.example.app.models.Leave;

@Service
public interface LeaveService {
	
	String requestLeave(Leave leave, Integer empId);
	
	String responseLeave(Integer id, Boolean status);
	
	List<Leave> allLeave(Integer empId);

	String newHoliday(Holiday holiday);

	List<HoildayDto> getAllHoliday();


    List<List<EmpAttendace>> allAttendance(Month month, Year year);
}
