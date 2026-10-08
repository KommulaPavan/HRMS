package com.example.Portal.Entity;

import jakarta.persistence.*;

@Entity
@Table
public class JobOpening {

    @Id
    @GeneratedValue
    private Long id;


    private String title;
    private String department;
    @Enumerated(EnumType.STRING)
    private com.example.Portal.Entity.JobType type;
    private String location;
    private int openings;
    private double salaryMin;
    private double salaryMax;
    private String salaryRange;
    private String description;

    @Enumerated(EnumType.STRING)
    private com.example.Portal.Entity.JobStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id",nullable = false)
    private Employee employee;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.example.Portal.Entity.JobType getType() {
        return type;
    }

    public void setType(com.example.Portal.Entity.JobType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOpenings() {
        return openings;
    }

    public void setOpenings(int openings) {
        this.openings = openings;
    }

    public double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public com.example.Portal.Entity.JobStatus getStatus() {
        return status;
    }

    public void setStatus(com.example.Portal.Entity.JobStatus status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setSalaryRange(double salaryMax, double salaryMin) {
        this.salaryMax=salaryMax;
        this.salaryMin=salaryMin;
    }
}
