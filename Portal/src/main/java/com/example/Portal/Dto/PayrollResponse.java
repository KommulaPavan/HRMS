package com.example.Portal.Dto;

import com.example.Portal.Entity.Payroll;
import com.example.Portal.Entity.PayrollStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayrollResponse {
    private String employeeId;

    private String name;
    private String email;
    private String department;

   private String month;

    private BigDecimal baseSalary;

    private Integer paidDays;
    private Integer workingDays;


    private BigDecimal gross;


    private BigDecimal pf;
    private BigDecimal tax;
    private BigDecimal totalDeductions;

    private BigDecimal net;


    private PayrollStatus status;

    private LocalDateTime createdAt;

    public PayrollResponse(){
    }



    public static PayrollResponse toEntity(Payroll p){
        PayrollResponse thiss=new PayrollResponse();
        thiss.employeeId = p.getEmployee().getEmployeeId();
        thiss.name = p.getEmployee().getName();
        thiss.email = p.getEmployee().getEmail();
        thiss.department = p.getEmployee().getDepartment();
        thiss.month = p.getMonth();
        thiss.baseSalary = p.getEmployee().getBaseSalary();
        thiss.paidDays = p.getPaidDays();
        thiss.workingDays = p.getWorkingDays();
        thiss.gross = p.getGross();
        thiss.pf = p.getPf();
        thiss.tax = p.getTax();
        thiss.totalDeductions = p.getTotalDeductions();
        thiss.net = p.getNet();
        thiss.status = p.getStatus();
        thiss.createdAt = p.getCreatedAt();

        return thiss;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Integer getPaidDays() {
        return paidDays;
    }

    public void setPaidDays(Integer paidDays) {
        this.paidDays = paidDays;
    }

    public Integer getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Integer workingDays) {
        this.workingDays = workingDays;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getPf() {
        return pf;
    }

    public void setPf(BigDecimal pf) {
        this.pf = pf;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(BigDecimal totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public PayrollStatus getStatus() {
        return status;
    }

    public void setStatus(PayrollStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
