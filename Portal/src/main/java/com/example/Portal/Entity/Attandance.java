package com.example.Portal.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
//    @Column(name = "employeeId", nullable = false, length = 64)
//   private String employeeId;
    private String source;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee employee;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
