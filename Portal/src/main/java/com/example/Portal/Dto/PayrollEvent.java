package com.example.Portal.Dto;

import java.math.BigDecimal;

public class PayrollEvent {

    private String employeeId;
    private String email;
    private BigDecimal net;
    private String month;

    public PayrollEvent(){}

    public PayrollEvent(String employeeId, String email, BigDecimal netSalary, String month) {
        this.employeeId=employeeId;
        this.email=email;
        this.net=net;
        this.month=month;
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

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
