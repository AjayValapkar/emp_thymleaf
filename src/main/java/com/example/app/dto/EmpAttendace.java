package com.example.app.dto;

import com.example.app.models.Employee;

import java.time.LocalDate;

public class EmpAttendace {

   private Employee employee;
   private LocalDate date;
   private boolean status;

    public EmpAttendace() {
    }

    public EmpAttendace(LocalDate date, Employee employee, boolean status) {
        this.date = date;
        this.employee = employee;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmpAttendace{" +
                "date=" + date +
                ", employee=" + employee +
                ", status=" + status +
                '}';
    }
}
