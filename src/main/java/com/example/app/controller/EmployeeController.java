package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.app.models.Employee;
import com.example.app.service.EmploeeService;

@Controller
@RequestMapping
public class EmployeeController {

	@Autowired
	EmploeeService empService;

	@PostMapping("/add")
	public String addNewEmployee(@ModelAttribute("employee") Employee emp) {
		String success = empService.addNewEmp(emp);
		return "redirect:/";
	}

	@GetMapping("/")
	public String fetchEmployee(Model model) {
		model.addAttribute("employee", empService.fetchAllEmp());
		return "index";
	}

	@RequestMapping("/employee")
	public String newEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("contentFragment", "newEmployee");
		return "index";
	}

	@GetMapping("/getEmp/{empId}")
	public String getEmployee(@PathVariable(value = "empId") Integer empId, Model model) {
		Employee employee = empService.getEmpById(empId);
		model.addAttribute("employee", employee);
		model.addAttribute("empid", empId);
		return "empupdate";
	}

	@RequestMapping("/delete/emp/{empId}")
	public String deleteEmp(@PathVariable(value = "empId") Integer empId, Model model) {
		String success = empService.deleteEmployee(empId);
		model.addAttribute("empid", empId);
		return "redirect:/";
	}

	@RequestMapping("/update/emp/{empId}")
	public String updateEm(@ModelAttribute(value = "employee") Employee employee,
			@PathVariable(value = "empId") Integer empId) {
		String empName = empService.updateEmp(employee, empId);
		return "redirect:/";
	}

}
