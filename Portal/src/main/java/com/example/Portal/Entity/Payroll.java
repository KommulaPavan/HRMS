package com.example.Portal.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payroll",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_payroll_employee_month",
                columnNames = {"employee_fk", "month"}
        )
)
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payroll_seq")
    @SequenceGenerator(
            name = "payroll_seq",
            sequenceName = "payroll_seq",
            allocationSize = 1
    )
    private Long id;

    // 🔑 Business reference (not FK)
    @Column(nullable = false, length = 64)
    private String employeeId;

    private String name;
    private String email;
    private String department;

    @Column(nullable = false, length = 7) // yyyy-MM
    private String month;

    private BigDecimal baseSalary;

    private Integer paidDays;
    private Integer workingDays;

    // Earnings
    private BigDecimal gross;

    // Deductions
    private BigDecimal pf;
    private BigDecimal tax;
    private BigDecimal totalDeductions;

    private BigDecimal net;

    @Enumerated(EnumType.STRING)
    private com.example.Portal.Entity.PayrollStatus status;

    private LocalDateTime createdAt;

    // 🔥 ONE-TO-ONE (MANDATORY)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "employee_fk",
            nullable = false,
            referencedColumnName = "id"
    )
    private Employee employee;



    // ---------- getters & setters ----------

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

    public com.example.Portal.Entity.PayrollStatus getStatus() {
        return status;
    }

    public void setStatus(com.example.Portal.Entity.PayrollStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
