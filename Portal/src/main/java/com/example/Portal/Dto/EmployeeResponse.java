package com.example.Portal.Dto;

import com.example.Portal.Entity.Employee;

public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;
    private String designation;
    private String employeeId;
    private String accountStatus;

    public EmployeeResponse() {}

    public static EmployeeResponse fromEntity(Employee e) {
        EmployeeResponse r = new EmployeeResponse();
        r.setId(e.getId());
        r.setName(e.getName());
        r.setEmail(e.getEmail());

        if (e.getRole() != null && !e.getRole().isEmpty()) {
            r.setRole(e.getRole().iterator().next().getName());
        }


        r.setDepartment(e.getDepartment());
        r.setDesignation(e.getDesignation());
        r.setEmployeeId(e.getEmployeeId());


        r.setAccountStatus(
                e.getAccountStatus() != null ? e.getAccountStatus() : "PENDING"
        );
        return r;
    }

    // --- getters/setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
}
