package com.example.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.models.EmpDateTime;
import com.example.app.models.Employee;
import com.example.app.models.Leave;
import com.example.app.repository.AddressRepository;
import com.example.app.repository.EmpDateTimeRepository;
import com.example.app.repository.EmployeeRepository;
import com.example.app.repository.LeaveRepository;
import com.example.app.service.EmploeeService;

@Service
public class EmploeeServiceImpl implements EmploeeService {

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	AddressRepository addRepo;

	@Autowired
	EmpDateTimeRepository empDateRepo;

	@Autowired
	LeaveRepository leaveRepo;

	PasswordGenerator pass = new PasswordGenerator();

	@Override
	public String addNewEmp(Employee emp) {
		emp.setPassword(pass.generateRandomPassword(8));
		emp.setEmpId(getNextAvailableId());
		empRepo.save(emp);
		return emp.getEmpName();
	}

	@Override
	public List<Employee> fetchAllEmp() {
		// TODO Auto-generated method stub
		return empRepo.findAll();
	}

	@Override
	public Employee getEmpById(Integer empId) {
		// TODO Auto-generated method stub
		Employee employee = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		return employee;
	}

	@Override
	public String updateEmp(Employee emp, Integer empId) {
		Employee employee = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		employee.setEmpId(empId);
		employee.setEmpName(emp.getEmpName());
		employee.setEmpEmail(emp.getEmpEmail());
		employee.setEmpMob(emp.getEmpMob());
		empRepo.save(employee);

		return emp.getEmpName();
	}

	@Override
	public String deleteEmployee(Integer empId) {
		// TODO Auto-generated method stub
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		try {
			empRepo.deleteById(empId);
			return emp.getEmpName();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public Integer getNextAvailableId() {
		List<Integer> existingIds = empRepo.findAllIds(); 
		Integer nextId = 1;

		while (existingIds.contains(nextId)) {
			nextId++;
		}

		return nextId;
	}

	@Override
	public String empPunch(EmpDateTime empdatetime, Integer empId) {
		// TODO Auto-generated method stub
		if (empdatetime.getPunchIn() == null) {
			return "Punch-In should not be null";
		}
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		empdatetime.setEmployee(emp);
		LocalDateTime currentDate = LocalDateTime.now();
		if (empdatetime.getPunchIn().toLocalDate().equals(currentDate.toLocalDate())) {
			if (getPunch(empId) == 0) {
				if (empdatetime.getPunchIn()!= null && empdatetime.getPunchIn().getHour() > 10 && empdatetime.getPunchIn().getHour() < 14) {
					createLeave("Half Day Leave", empdatetime.getPunchIn().toLocalDate(), emp,
							"Due to missed punching time");
				} else if (empdatetime.getPunchIn()!= null && empdatetime.getPunchIn().getHour() > 14 || (empdatetime.getPunchOut()!=null && empdatetime.getPunchOut().getHour() < 14)) {
					createLeave("Full Day Leave", empdatetime.getPunchIn().toLocalDate(), emp,
							"Due to missed punching time");
				}
				emp.getDatetime().add(empdatetime);
				empDateRepo.save(empdatetime);
				empRepo.save(emp);
				return "Punching done Succefully";
			} else {
				return "You can punch-In once in a day";
			}
		}
		return "You are punching on Different date";
	}

	@Override
	public String leaveDueNoPunch(Integer empId) {
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		Calendar calendar = Calendar.getInstance();
		LocalDate today = LocalDate.now();
		List<EmpDateTime> punch = emp.getDatetime();
		List<Integer> date = punch.stream()
				.filter(x -> x.getPunchIn().getMonthValue() == LocalDate.now().getMonthValue())
				.map(x -> x.getPunchIn().getDayOfMonth()).collect(Collectors.toList());
		System.out.println(date);
		for (int day = 1; day < today.getDayOfMonth(); day++) {
			LocalDate currentDate = today.minusDays(1).withDayOfMonth(day);
			calendar.set(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

			System.out.println(dayOfWeek + " DAY IS " + day + " YEAR " + currentDate.getYear());
        
			if (!date.contains(day) && dayOfWeek != Calendar.SUNDAY && dayOfWeek != Calendar.SATURDAY) {
				createLeave("Full Day Leave", currentDate, emp, "Due to Missed Punching");
				System.out.println(currentDate);

			}
		}
		return "";
	}

	private void createLeave(String leaveType, LocalDate date, Employee emp, String msg) {

		Leave leave = new Leave();
		leave.setLeaveCategory(leaveType);
		leave.setReason(msg);
		leave.setSingleDay(date);
		leave.setStatus(true);
		List<LocalDate> single = emp.getLeaves().stream().map(Leave::getSingleDay).collect(Collectors.toList());
		List<LocalDate> from = emp.getLeaves().stream().map(Leave::getLeaveFrom).collect(Collectors.toList());
		List<LocalDate> to = emp.getLeaves().stream().map(Leave::getLeaveTo).collect(Collectors.toList());
		
		if ((!single.contains(date) && !from.contains(date) && !to.contains(date))) {
			emp.getLeaves().add(leave);
			leave.setEmployee(emp);
			leaveRepo.save(leave);
		}
	}

	@Override
	public String empPunchOut(EmpDateTime empdatetime, Integer empId, Integer id) {
		// TODO Auto-generated method stub
		if (empdatetime.getPunchOut() == null) {
			return "Punch-Out should not be null";
		}
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		Integer puncId = 0;
		LocalDateTime currentDate = LocalDateTime.now();
		List<EmpDateTime> datee = emp.getDatetime();
		for (EmpDateTime element : datee) {
			System.out.println(element.getPunchIn());
			if (element.getPunchIn() == null) {
				puncId = 0;

			} else if (currentDate.toLocalDate().equals(element.getPunchIn().toLocalDate())) {
				puncId = element.getId();
				System.out.print(puncId);
			}
		}

		EmpDateTime empDate = empDateRepo.findById(puncId).orElse(null);
		if (empDate == null) {
			return "You can't punch-out before Punch in";
		} else if (empDate.getPunchOut() != null) {
			if (empDate.getPunchOut().toLocalDate().equals(empdatetime.getPunchOut().toLocalDate())) {
				return "You can punch-Out once in a day";
			}
			return "You can punch-Out once in a day";
		} else if (empdatetime.getPunchOut().toLocalDate().equals(currentDate.toLocalDate())
				&& empdatetime.getPunchOut().getHour() > empDate.getPunchIn().getHour()) {
			empDate.setPunchOut(empdatetime.getPunchOut());
			Integer total = (empdatetime.getPunchOut().getHour() - empDate.getPunchIn().getHour());
			empDate.setTotalHours(total);
			empDateRepo.save(empDate);
			return "Punching done Succefully";
		} else {
			return "You are punching for different date";
		}
	}

	@Override
	public Integer getPunch(Integer empId) {
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		Integer puncId = 0;
		LocalDateTime currentDate = LocalDateTime.now();
//        LocalDateTime currentDate = emp.getDatetime().get(0).getPunchIn();
		List<EmpDateTime> datee = emp.getDatetime();

		for (EmpDateTime element : datee) {
			System.out.print("Punch Time" + element.getPunchIn());
			if (element.getPunchIn() == null) {
				return 0;
			} else if (currentDate.toLocalDate().equals(element.getPunchIn().toLocalDate())) {
				puncId = element.getId();
				System.out.print(puncId);
			}
		}

		System.out.print(currentDate.toLocalDate());
		System.out.print(puncId);
		return puncId;
	}

	@Override
	public LocalDateTime getTodayPunch(Integer empId) {
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		List<EmpDateTime> punch = emp.getDatetime().stream()
				.filter(x -> x.getPunchIn().getDayOfMonth() == LocalDate.now().getDayOfMonth()).toList();
		if (punch.isEmpty()) {
			return LocalDateTime.now();
		}
		return punch.get(0).getPunchIn();
	}

	@Override
	public List<EmpDateTime> getEmpAtt(Integer empId) {
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));

		return emp.getDatetime();
	}

}
