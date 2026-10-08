package com.example.Portal.Dto;

import com.example.Portal.Entity.Attandance;

import java.time.LocalDate;

public class AttadanceRecord {

    private String employeeId;
    private LocalDate date;
    private String status;

    public AttadanceRecord(){}

    public AttadanceRecord(Attandance a){
        if(a.getEmployee()!=null) {
            this.employeeId = a.getEmployee().getEmployeeId();
        }
        this.date=a.getTodayDate();
        this.status=a.getAttandancestatus();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
