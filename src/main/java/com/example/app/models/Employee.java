package com.example.app.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	private Integer empId;

	private String empName;
	private String empEmail;
	private String empMob;

	private String password;
	
	private Double salary;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = false)
	List<Address> address;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = false)
	List<EmpDateTime> datetime;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = false)
	List<Leave> leaves;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = false)
    List<Payment> payment;

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(Integer empId, String empName, String empEmail, String empMob, String password,
			Double salary,
			List<Address> address, List<EmpDateTime> datetime, List<Leave> leaves) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empEmail = empEmail;
		this.empMob = empMob;
		this.password = password;
		this.address = address;
		this.datetime = datetime;
		this.leaves = leaves;
		this.salary= salary;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getEmpMob() {
		return empMob;
	}

	public void setEmpMob(String empMob) {
		this.empMob = empMob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<EmpDateTime> getDatetime() {
		return datetime;
	}

	public void setDatetime(List<EmpDateTime> datetime) {
		this.datetime = datetime;
	}

	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}
	

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empEmail=" + empEmail + ", empMob=" + empMob
				+ ", password=" + password + ", address=" + address + ", datetime=" + datetime + "]";
	}

}
