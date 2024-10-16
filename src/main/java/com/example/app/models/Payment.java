package com.example.app.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private LocalDate payDate;
	private Double baseSalary;
	private Double professionalTax;
	private Double monthPf;
	private Double takeHomeSalary;
	
	@ManyToOne
	@JoinColumn(name = "emp_id", nullable = false, referencedColumnName = "empId")
	private Employee employee;

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(Long id, Double baseSalary, Double professionalTax, Double monthPf, Double takeHomeSalary) {
		super();
		this.id = id;
		this.baseSalary = baseSalary;
		this.professionalTax = professionalTax;
		this.monthPf = monthPf;
		this.takeHomeSalary = takeHomeSalary;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Double getProfessionalTax() {
		return professionalTax;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Double getTakeHomeSalary() {
		return takeHomeSalary;
	}

	public void setProfessionalTax(Double professionalTax) {
		this.professionalTax = professionalTax;
	}

	public Double getMonthPf() {
		return monthPf;
	}

	public void setMonthPf(Double monthPf) {
		this.monthPf = monthPf;
	}

	public Double getTakeHomeSalary(Double monthSalary) {
		return takeHomeSalary;
	}

	public void setTakeHomeSalary(Double takeHomeSalary) {
		this.takeHomeSalary = takeHomeSalary;
	}
	public LocalDate getPayDate() {
		return payDate;
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", baseSalary=" + baseSalary + ", professionalTax=" + professionalTax
				+ ", monthPf=" + monthPf + ", takeHomeSalary=" + takeHomeSalary + "]";
	}

}
