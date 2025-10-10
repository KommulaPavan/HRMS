package com.example.Portal.Dto;

import java.util.List;

public class EnrollRequest {
    private List<String> employeeIds;

    public EnrollRequest() {}
    public EnrollRequest(List<String> employeeIds) { this.employeeIds = employeeIds; }

    public List<String> getEmployeeIds() { return employeeIds; }
    public void setEmployeeIds(List<String> employeeIds) { this.employeeIds = employeeIds; }
}
