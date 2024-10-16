package com.example.app.dto;

import java.time.LocalDate;
import java.time.Month;

public class HoildayDto {

    private Long id;
    private Month month;
    private String title;
    private LocalDate date;

    public HoildayDto(Long id, Month month, String title, LocalDate date) {
        this.id = id;
        this.month = month;
        this.title = title;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HoildayDto{" +
                "id=" + id +
                ", month=" + month +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
