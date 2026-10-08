package com.example.Portal.Dto;

import com.example.Portal.Entity.Attandance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttandanceRequest {

    private String notes;
    private LocalDate todayDate;
    private String attandancestatus;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;
    private int workMinutes;
    private String employeeId;   // keep this
    private String source;

    public AttandanceRequest() {
    }

    public AttandanceRequest(Attandance a){
        this.notes = a.getNotes();
        this.todayDate = a.getTodayDate();
        this.attandancestatus = a.getAttandancestatus();
        this.checkInAt = a.getCheckInAt();
        this.checkOutAt = a.getCheckOutAt();
        this.workMinutes = a.getWorkMinutes();
        this.source = a.getSource();

        // IMPORTANT: map from relation
        if (a.getEmployee() != null) {
            this.employeeId = a.getEmployee().getEmployeeId();
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public String getAttandancestatus() {
        return attandancestatus;
    }

    public void setAttandancestatus(String attandancestatus) {
        this.attandancestatus = attandancestatus;
    }

    public LocalDateTime getCheckInAt() {
        return checkInAt;
    }

    public void setCheckInAt(LocalDateTime checkInAt) {
        this.checkInAt = checkInAt;
    }

    public LocalDateTime getCheckOutAt() {
        return checkOutAt;
    }

    public void setCheckOutAt(LocalDateTime checkOutAt) {
        this.checkOutAt = checkOutAt;
    }

    public int getWorkMinutes() {
        return workMinutes;
    }

    public void setWorkMinutes(int workMinutes) {
        this.workMinutes = workMinutes;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
