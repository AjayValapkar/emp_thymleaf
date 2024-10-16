package com.example.app.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "holiday")
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String holidayTitle;
    private LocalDate date ;

    public Holiday(Long id) {
        this.id = id;
    }

    public Holiday(Long id, String holidayTitle, LocalDate date) {
        this.id = id;
        this.holidayTitle = holidayTitle;
        this.date = date;
    }

    public Holiday() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayTitle() {
        return holidayTitle;
    }

    public void setHolidayTitle(String holidayTitle) {
        this.holidayTitle = holidayTitle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", holidayTitle='" + holidayTitle + '\'' +
                ", date=" + date +
                '}';
    }
}
