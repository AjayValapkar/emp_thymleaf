package com.example.app.models;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "empdatetime")
public class EmpDateTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDateTime punchIn;
	private LocalDateTime punchOut;

	@ManyToOne
	@JoinColumn(name = "emp_id", nullable = false, referencedColumnName = "empId")
	private Employee employee;
	
	private Integer totalHours;
	

	public Integer getTotalHours() {
		return totalHours;
	}
// This line Change
	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	public EmpDateTime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpDateTime(Integer id, LocalDateTime punchIn, LocalDateTime punchOut, Employee employee) {
		super();
		this.id = id;
		this.punchIn = punchIn;
		this.punchOut = punchOut;
		this.employee = employee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getPunchIn() {
		return punchIn;
	}

	public void setPunchIn(LocalDateTime punchIn) {
		this.punchIn = punchIn;
	}

	public LocalDateTime getPunchOut() {
		return punchOut;
	}

	public void setPunchOut(LocalDateTime punchOut) {
		this.punchOut = punchOut;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "EmpDateTime [id=" + id + ", punchIn=" + punchIn + ", punchOut=" + punchOut + ", employee=" + employee
				+ "]";
	}

}
