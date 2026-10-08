package com.example.Portal.Dto;

import com.example.Portal.Entity.JobOpening;

import java.time.LocalDateTime;

public class JobRequest {
    private Long id;
    private String employeeId;
    private String title;
    private String department;
    private String type;
    private String location;
    private int openings;
    private double salaryRange;
    private String status;
    private double salaryMin;
    private double salaryMax;
    private LocalDateTime createdAt;

    public static JobRequest forEntity(JobOpening job){
        JobRequest j=new JobRequest();
        j.setId(job.getId());
        j.setTitle(job.getTitle());
        j.setDepartment(job.getDepartment());
        j.setType(job.getType().name());
        j.setLocation(job.getLocation());
        j.setStatus(job.getStatus().name());
        j.setSalaryMin(job.getSalaryMin());
        j.setSalaryMax(job.getSalaryMax());
        j.setOpenings(job.getOpenings());
        return j;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public double getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(double salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
}
