package com.example.Portal.Dto;

public class RosterItemDto {
    private String employeeId;
    private String status;   // "enrolled" | "completed" | etc.
    private Integer progress; // 0..100
    private String name;
    private String email;
    private String department;

    public RosterItemDto() {}

    public RosterItemDto(String employeeId, String status, Integer progress,
                         String name, String email, String department) {
        this.employeeId = employeeId;
        this.status = status;
        this.progress = progress;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
