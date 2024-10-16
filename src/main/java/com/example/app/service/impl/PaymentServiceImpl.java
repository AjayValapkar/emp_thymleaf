package com.example.app.service.impl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dto.PayLeave;
import com.example.app.models.Employee;
import com.example.app.models.Leave;
import com.example.app.models.Payment;
import com.example.app.repository.EmployeeRepository;
import com.example.app.repository.PaymentRepository;
import com.example.app.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	PaymentRepository payRepo;

	public Payment ctcToTakeHome(Double ctc, Integer fullLeave, Integer halfLeave) {
		LocalDate date = LocalDate.now();
		Payment payment = new Payment();
		final double tax_rate_1 = 5.0;
		final double tax_rate_2 = 10.0;
		final double tax_rate_3 = 15.0;
		final double pf = 12.0f;

		double tax = 0.0;
		if (ctc > 3 && ctc < 7) {
			tax = tax_rate_1;
		} else if (ctc > 7 && ctc < 12) {
			tax = tax_rate_2;
		} else if (ctc > 12) {
			tax = tax_rate_3;
		}

		ctc = ctc * 100000;
		Double totalPf = (ctc * pf) / 100;
		Double totalTax = (ctc * tax) / 100;
		Double takeHome = ctc - (totalPf + totalTax);
		Double monthSalary = takeHome / 12;
		Double salaryDedu = (halfLeave > 1) ? (monthSalary / 30) * ((halfLeave - 1) * 0.5) : 0.0;
		Double salaryDeduction = (fullLeave > 1) ? (monthSalary / 30) * (fullLeave - 1) : 0.0;
		monthSalary -= (salaryDeduction + salaryDedu);

		payment.setMonthPf(totalPf / 12);
		payment.setProfessionalTax(totalTax / 12);
		payment.setTakeHomeSalary(monthSalary);
		payment.setPayDate(date);
		
		return payment;
	}

	@Override
	public String doPayment(Integer empId) {
		// TODO Auto-generated method stub
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		Double ctc = emp.getSalary();
		List<Leave> leaves = emp.getLeaves();
		List<Leave> full = leaves.stream()
				.filter(leave -> (leave.getLeaveCategory().equals("Full Day Leave") && leave.getStatus()
						&& leave.getSingleDay() != null
						&& leave.getSingleDay().getMonth().equals(LocalDate.now().getMonth())))
				.toList();
		List<Leave> half = leaves.stream()
				.filter(leave -> (leave.getLeaveCategory().equals("Half Day Leave") && leave.getStatus()
						&& leave.getSingleDay() != null
						&& leave.getSingleDay().getMonth().equals(LocalDate.now().getMonth())))
				.toList();
		System.out.println("Full Leave More" + totalLeave(empId, LocalDate.now()));
        
		if (LocalDate.now().getDayOfMonth() == 30 && !emp.getPayment().contains(LocalDate.now())) {
			Payment payment = ctcToTakeHome(ctc, full.size() + totalLeave(empId, LocalDate.now()), half.size());
			payment.setEmployee(emp);
			payRepo.save(payment);
		}
		return "payment succefully";
	}
	


	public Integer totalLeave(Integer empId, LocalDate date) {
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		List<Leave> leave = emp.getLeaves();
		int count = 0;
		for (Leave leave2 : leave) {
			LocalDate leaveFrom = leave2.getLeaveFrom();
			LocalDate leaveTo = leave2.getLeaveTo();
			System.out.println(leaveFrom + ":" + leaveTo);
			if (leaveFrom != null && leaveTo != null && leave2.getStatus()) {
				if (leaveFrom.getMonth().equals(date.getMonth()) && leaveTo.getMonth().equals(date.getMonth())) {
					count += calculateDaysInMonth(leaveFrom, leaveTo);
					System.out.println("Last Date" + YearMonth.now().atEndOfMonth().getDayOfMonth());
				} else if (leaveTo.getMonth().equals(date.getMonth())
						&& !(leaveFrom.getMonth().equals(date.getMonth()))) {
					count += calculateDaysInMonth(YearMonth.now().atDay(1), leaveTo);
					System.out.println("First Date" + YearMonth.now().atDay(1));
				} else if ((leaveFrom.getMonth().equals(date.getMonth()))
						&& !(leaveTo.getMonth().equals(date.getMonth()))) {
					count += calculateDaysInMonth(leaveFrom, YearMonth.now().atEndOfMonth());
				}
			}
		}
		
		return count;
	}

	private int calculateDaysInMonth(LocalDate from, LocalDate to) {
		System.out.println(to.getDayOfMonth() - from.getDayOfMonth() + 1);
		return to.getDayOfMonth() - from.getDayOfMonth() + 1;
	}

	@Override
	public List<PayLeave> getAllPay(Integer empId) {
		// TODO Auto-generated method stub
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not found"));
		List<Payment> payments = emp.getPayment();
		List<Leave> leaves = emp.getLeaves();
		List<PayLeave> pays = new ArrayList<>();
		for (Payment pay : payments) {
			List<Leave> full = leaves.stream()
					.filter(leave -> (leave.getLeaveCategory().equals("Full Day Leave") && leave.getStatus()
							&& leave.getSingleDay() != null
							&& leave.getSingleDay().getMonth().equals(pay.getPayDate().getMonth())))
					.toList();
			List<Leave> half = leaves.stream()
					.filter(leave -> (leave.getLeaveCategory().equals("Half Day Leave") && leave.getStatus()
							&& leave.getSingleDay() != null
							&& leave.getSingleDay().getMonth().equals(pay.getPayDate().getMonth())))
					.toList();
			PayLeave payment = new PayLeave();
			payment.setFullLeave(full.size()+totalLeave(empId,pay.getPayDate()));
			payment.setHalfLeave(half.size());
			payment.setPayDate(pay.getPayDate());
			payment.setMonth(Month.of(pay.getPayDate().getMonthValue()));
			payment.setMonthPf(pay.getMonthPf());
			 DecimalFormat df = new DecimalFormat("#.00");
			payment.setMonthTax(pay.getProfessionalTax());
			payment.setTakeHomeSalary(df.format(pay.getTakeHomeSalary()));
			pays.add(payment);
		}

		return pays;
	}

}
