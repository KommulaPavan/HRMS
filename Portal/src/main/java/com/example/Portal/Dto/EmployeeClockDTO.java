package com.example.Portal.Dto;

import com.example.Portal.Entity.Employee;

public class EmployeeClockDTO {

    private String employeeId;
    private String name;
    private String email;

    private EmployeeClockDTO(){}

    public EmployeeClockDTO(Employee e) {
        this.employeeId = e.getEmployeeId();
        this.name = e.getName();
        this.email = e.getEmail();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
