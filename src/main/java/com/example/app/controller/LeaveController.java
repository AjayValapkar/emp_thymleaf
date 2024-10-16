package com.example.app.controller;


import com.example.app.models.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.models.Leave;
import com.example.app.service.LeaveService;

@Controller
public class LeaveController {

	@Autowired
	LeaveService leaveSer;

	@RequestMapping("/leave/{empId}")
	public String leave(Model model, @PathVariable Integer empId) {
		model.addAttribute("leave", new Leave());
		model.addAttribute("empId", empId);
		model.addAttribute("leaves", leaveSer.allLeave(empId));
		return "leave";
	}

	@PostMapping("/leaveReq/{empId}")
	public String reqLeave(@ModelAttribute(value = "leave") Leave leave, @PathVariable Integer empId, Model model) {
		String srt = leaveSer.requestLeave(leave, empId);
		model.addAttribute("msg", srt);
		model.addAttribute("leaves", leaveSer.allLeave(empId));
		return "redirect:/leave/" + empId;
	}

	@GetMapping("/leaves/{empId}")
	public String allEmpLeave(@PathVariable Integer empId, Model model) {
		model.addAttribute("leaves", leaveSer.allLeave(empId));
		model.addAttribute("empId", empId);
		return "leaves";
	}

	@RequestMapping("/leave/{status}/{id}/{empId}")
	public String responseLeave(@PathVariable Integer empId, @PathVariable Integer id, Model model,
			@PathVariable Boolean status) {
		leaveSer.responseLeave(id, status);
		return "redirect:/leaves/" + empId;
	}

	@PostMapping("/add/holidays")
	public String newHoliday(@ModelAttribute("holiday") Holiday holiday, BindingResult result, Model model) {
		if (result.hasErrors()) {
			// Handle errors
			return "errorView"; // Replace with your actual error view
		}
		String msg = leaveSer.newHoliday(holiday);
		model.addAttribute("holidaymsg", msg);
		return "redirect:/holidays";
	}




}
