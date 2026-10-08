package com.example.Portal.Entity;

import jakarta.persistence.*;

@Entity
@Table
public class EmployeeProfile {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "employeeId", nullable = false, length = 64)
    private String employeeId;
    private String name;
    private String email;
    private String department;
    private Long phone;
    private String location;
    @Lob
    @Column(name = "ABOUT")              // optional: specify column name
    private String about;

    @Lob
    @Column(name = "AVATAR_URL")         // if you absolutely want to store long urls/data
    private String avatarUrl;
    private String role;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_fk", referencedColumnName = "id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
