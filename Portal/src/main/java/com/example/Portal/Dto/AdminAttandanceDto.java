package com.example.Portal.Dto;


import com.example.Portal.Entity.Attandance;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Role;

import java.time.LocalDate;

public class AdminAttandanceDto {

    private String employeeId;
    private LocalDate todayDate;
    private String attandanceStatus;
    private String email;        // NEW
    private String department;   // NEW
    private String designation;
    private String role;
    private String accountStatus;
     public AdminAttandanceDto(){}
    public AdminAttandanceDto(Attandance a){
    this.todayDate=a.getTodayDate();
    this.attandanceStatus=a.getAttandancestatus();
    Employee emp=a.getEmployee();
    if(emp !=null){
        this.email=emp.getEmail();
        this.employeeId=emp.getEmployeeId();
        this.department=emp.getDepartment();
        this.designation=emp.getDesignation();
        this.accountStatus=emp.getAccountStatus();
        this.role=emp.getRole().stream().findFirst().map(Role::getName).orElse(null);
    }


    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public String getAttandanceStatus() {
        return attandanceStatus;
    }

    public void setAttandanceStatus(String attandanceStatus) {
        this.attandanceStatus = attandanceStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
