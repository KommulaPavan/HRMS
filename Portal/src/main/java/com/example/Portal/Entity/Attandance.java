package com.example.Portal.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attandance",
        uniqueConstraints = @UniqueConstraint(name = "uk_attandance_emp_date",
                columnNames = {"employee_id","today_date"}))
public class Attandance {
    @Id
    @GeneratedValue
    private Long id;

    private String notes;
    private LocalDate todayDate;
    private String attandancestatus;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;
    private int workMinutes;
//    @Column(name = "empId", nullable = false, length = 64)
//    private String employeeId;
    @Enumerated(EnumType.STRING)
    private com.example.Portal.Entity.AttandanceStatus status;
    private String source;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EMPLOYEE_ID")
    private com.example.Portal.Entity.Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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



    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public com.example.Portal.Entity.Employee getEmployee() {
        return employee;
    }

    public void setEmployee(com.example.Portal.Entity.Employee employee) {
        this.employee = employee;
    }

//    public String getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(String employeeId) {
//        this.employeeId = employeeId;
//    }

    public com.example.Portal.Entity.AttandanceStatus getStatus() {
        return status;
    }

    public void setStatus(com.example.Portal.Entity.AttandanceStatus status) {
        this.status = status;
    }
}
