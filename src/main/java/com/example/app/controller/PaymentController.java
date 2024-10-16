package com.example.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.dto.PayLeave;
import com.example.app.models.Leave;
import com.example.app.service.LeaveService;
import com.example.app.service.PaymentService;

@Controller
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@Autowired
	LeaveService leaveSer;

	@RequestMapping("/payment/{empId}")
	public String paymentPage(@PathVariable Integer empId, Model model) {
		model.addAttribute("empId", empId);
		model.addAttribute("payment", getAllPayment(empId));
        List<Leave> leave = leaveSer.allLeave(empId);
		model.addAttribute("leaves", leaveSer.allLeave(empId));
		return "salarydetails";
	}

	@RequestMapping("/pay/{empId}")
	public String makePayment(@PathVariable Integer empId, Model model) {
		model.addAttribute("empId", empId);
	    String str = paymentService.doPayment(empId);
		return "redirect:/";
	}

	@GetMapping("/emp/payment/{empId}")
	public List<PayLeave> getAllPayment(@PathVariable Integer empId) {
		return paymentService.getAllPay(empId);
	}

}
