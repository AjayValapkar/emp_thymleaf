package com.example.app.dto;

import java.time.LocalDate;
import java.time.Month;

public class PayLeave {

	private Month month;
	private LocalDate payDate;
	private Integer fullLeave;
	private Integer halfLeave;
	private Double monthPf;
	private Double monthTax;
	private String takeHomeSalary;

	public PayLeave() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayLeave(Month month, LocalDate payDate, Integer fullLeave, Integer halfLeave, Double monthPf,
			Double monthTax, String takeHomeSalary) {
		super();
		this.month = month;
		this.payDate = payDate;
		this.fullLeave = fullLeave;
		this.halfLeave = halfLeave;
		this.monthPf = monthPf;
		this.monthTax = monthTax;
		this.takeHomeSalary = takeHomeSalary;
	}

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month2) {
		this.month = month2;
	}

	public LocalDate getPayDate() {
		return payDate;
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	public Integer getFullLeave() {
		return fullLeave;
	}

	public void setFullLeave(Integer fullLeave) {
		this.fullLeave = fullLeave;
	}

	public Integer getHalfLeave() {
		return halfLeave;
	}

	public void setHalfLeave(Integer halfLeave) {
		this.halfLeave = halfLeave;
	}

	public Double getMonthPf() {
		return monthPf;
	}

	public void setMonthPf(Double monthPf) {
		this.monthPf = monthPf;
	}

	public Double getMonthTax() {
		return monthTax;
	}

	public void setMonthTax(Double monthTax) {
		this.monthTax = monthTax;
	}

	public String getTakeHomeSalary() {
		return takeHomeSalary;
	}

	public void setTakeHomeSalary(String takeHomeSalary) {
		this.takeHomeSalary = takeHomeSalary;
	}

	@Override
	public String toString() {
		return "PayLeave [month=" + month + ", payDate=" + payDate + ", fullLeave=" + fullLeave + ", halfLeave="
				+ halfLeave + ", monthPf=" + monthPf + ", monthTax=" + monthTax + ", takeHomeSalary=" + takeHomeSalary
				+ "]";
	}

}
