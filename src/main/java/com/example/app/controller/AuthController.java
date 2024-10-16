package com.example.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.dto.LoginDto;
import com.example.app.models.EmpDateTime;
import com.example.app.models.Employee;
import com.example.app.service.AuthService;
import com.example.app.service.EmploeeService;

@Controller
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	EmploeeService empService;

	@RequestMapping("/loginPanel")
	public String dash(Model model) {
		model.addAttribute("logindto", new LoginDto());
		return "login";
	}

	@RequestMapping("/login")
	public String empLogin(@ModelAttribute(value = "login") LoginDto login, Model model) {
		Employee employee = authService.loginEmployee(login);
		System.out.println(employee.getEmpEmail());
		model.addAttribute("login", login);
		model.addAttribute("empId", employee.getEmpId());
		model.addAttribute("empdatetime", new EmpDateTime());
		model.addAttribute("employee", employee);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy'T'HH:mm");
		String formattedTime = LocalDateTime.now().format(formatter);
		model.addAttribute("time", formattedTime);
		System.out.println("TIMING" + LocalDateTime.now());
		Integer id = empService.getPunch(employee.getEmpId());
		System.out.println("id is" + id);
		String strr = empService.leaveDueNoPunch(employee.getEmpId());
		model.addAttribute("id", id);
		model.addAttribute("time", empService.getTodayPunch(employee.getEmpId()).getHour() + ":"
				+ empService.getTodayPunch(employee.getEmpId()).getMinute());

		return "dashboard";
	}

	@PostMapping("/punch/{empId}")
	public String empPunchIn(@PathVariable Integer empId,
			@ModelAttribute(value = "empdatetime") EmpDateTime empdatetime, Model model) {
		model.addAttribute("emp", empdatetime);
		model.addAttribute("empId", empId);
		Employee employee = empService.getEmpById(empId);
		model.addAttribute("employee", employee);
		String str = empService.empPunch(empdatetime, empId);
		model.addAttribute("error", str);
		return "dashboard";
	}

	@PostMapping("/punchOut/{empId}/{id}")
	public String empPunchOut(@PathVariable Integer empId, @PathVariable Integer id,
			@ModelAttribute(value = "empdatetime") EmpDateTime empdatetime, Model model) {
		model.addAttribute("emp", empdatetime);
		model.addAttribute("empId", empId);
		Employee employee = empService.getEmpById(empId);
		model.addAttribute("employee", employee);
		String str = empService.empPunchOut(empdatetime, empId, id);
		model.addAttribute("msg", str);
		return "dashboard";
	}

	@GetMapping("/attendace/{empId}")
	public String getEmpAttendance(@PathVariable Integer empId, Model model) {
		List<EmpDateTime> attedance = empService.getEmpAtt(empId);
		model.addAttribute("employee", empService.getEmpById(empId));
		model.addAttribute("attendace", attedance);
		return "attendance";
	}

	@GetMapping("/holidays")
	public String holidays(Model model) {
		model.addAttribute("contentFragment", "holidays");
		return "index";
	}

	@GetMapping("/attendance")
	public String attendance(Model model) {
		model.addAttribute("contentFragment", "attendance");
		return "index";
	}

}
