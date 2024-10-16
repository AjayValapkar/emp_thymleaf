package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.models.Address;
import com.example.app.models.Employee;
import com.example.app.service.AddressService;
import com.example.app.service.EmploeeService;

@Controller
public class AddressController {

	@Autowired
	EmploeeService empService;

	@Autowired
	AddressService addService;

	@GetMapping("/getAddress/{id}/{empId}")
	public String getAddress(@PathVariable(value = "id") Integer id, Model model,
			@PathVariable(value = "empId") Integer empId) {
		Address address = addService.getAddress(id);
		model.addAttribute("empid", empId);
		model.addAttribute("address", address);
		return "updateadd";
	}

	@GetMapping("/address/{empId}")
	public String empAddress(@PathVariable Integer empId, Model model) {
		model.addAttribute("empid", empId);
		model.addAttribute("address", addService.empAdd(empId));
		return "address";
	}

	@PostMapping("/add/address/{empId}")
	public String addEmpAddress(@ModelAttribute(value = "address") Address address, @PathVariable Integer empId,
			Model model) {
		String success = addService.addEmpAddress(address, empId);
		model.addAttribute("empid", empId);
		return "address";
	}

	@RequestMapping("/add/{empid}")
	public String newEmployee(Model model, @PathVariable Integer empid) {
		model.addAttribute("empid", empid);
		model.addAttribute("address", new Address());
		return "add";
	}

	@RequestMapping("/delete/address/{id}/{empId}")
	public String deleteAddress(@PathVariable Integer id, @PathVariable Integer empId, Model model) {
		String empName = addService.deleteAddress(id);
		return "redirect:/address/" + empId;
	}

	@RequestMapping("/update/add/{id}/{empId}")
	public String updateEmpAdd(@PathVariable Integer id, @ModelAttribute(value = "address") Address addree,
			@PathVariable(value = "empId") Integer empId) {
		String empName = addService.updateAddress(id, addree);
		return "redirect:/address/" + empId;
	}

}
