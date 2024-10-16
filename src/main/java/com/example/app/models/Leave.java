package com.example.app.models;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "leaves")
public class Leave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDate leaveFrom;
	private LocalDate leaveTo;
	private LocalDate singleDay;
	private String leaveCategory;
	private String reason;
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "emp_id", nullable = false, referencedColumnName = "empId")
	private Employee employee;

	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Leave(Integer id, LocalDate leaveFrom, LocalDate leaveTo, LocalDate singleDay, String leaveCategory, String reason,
			Boolean status, Employee employee) {
		super();
		this.id = id;
		this.leaveFrom = leaveFrom;
		this.leaveTo = leaveTo;
		this.singleDay = singleDay;
		this.leaveCategory = leaveCategory;
		this.reason = reason;
		this.status = status;
		this.employee = employee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getLeaveFrom() {
		return leaveFrom;
	}

	public void setLeaveFrom(LocalDate leaveFrom) {
		this.leaveFrom = leaveFrom;
	}

	public LocalDate getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(LocalDate leaveTo) {
		this.leaveTo = leaveTo;
	}

	public LocalDate getSingleDay() {
		return singleDay;
	}

	public void setSingleDay(LocalDate singleDay) {
		this.singleDay = singleDay;
	}

	public String getLeaveCategory() {
		return leaveCategory;
	}

	public void setLeaveCategory(String leaveCategory) {
		this.leaveCategory = leaveCategory;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Leave [id=" + id + ", leaveFrom=" + leaveFrom + ", leaveTo=" + leaveTo + ", singleDay=" + singleDay
				+ ", leaveCategory=" + leaveCategory + ", reason=" + reason + ", status=" + status + ", employee="
				+ employee + "]";
	}

}
