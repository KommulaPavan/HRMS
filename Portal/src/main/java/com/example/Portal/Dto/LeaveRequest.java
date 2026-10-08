package com.example.Portal.Dto;


import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Leave;
import com.example.Portal.Entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveRequest {

    private Long id;
    private String name;
    private String typeOf;
    private String leaveId;
    private String employeeId;   // NEW
    private String email;        // NEW
    private String department;   // NEW
    private String designation;
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    private int days;
    private String reason;
    private String status;
    private String approver;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private String remark;
    private LocalDateTime approvedAt;
    // ✅ Jackson needs this
    public LeaveRequest() {
    }

    // Your convenience ctor (fine to keep)
    public LeaveRequest(Leave leave) {
        this.id = leave.getId();
        this.leaveId=leave.getLeaveId();
        this.typeOf = leave.getTypeOf();
        this.fromDate = leave.getFromDate();
        this.toDate = leave.getToDate();
        this.days = leave.getDays();
        this.reason = leave.getReason();
        this.status = leave.getStatus();
        this.createdAt = leave.getCreatedAt();
        this.name = (leave.getEmployee() != null) ? leave.getEmployee().getName() : "Unknown";
        this.approver=leave.getApprover();
        this.remark=leave.getRemark();
        this.approvedAt=leave.getApprovedAt();
        Employee emp = leave.getEmployee();
        if (emp != null) {
            this.name = emp.getName();
            this.employeeId = emp.getEmployeeId();     // or String.valueOf(emp.getId())
            this.email = emp.getEmail();
            this.department = emp.getDepartment();
            this.designation = emp.getDesignation();
            this.role=emp.getRole().stream().findFirst().map(Role::getName).orElse(null);
        } else {
            this.name = "Unknown";
        }

    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    //    public Employee getEmp() {
//        return emp;
//    }
//
//    public void setEmp(Employee emp) {
//        this.emp = emp;
//    }
}
