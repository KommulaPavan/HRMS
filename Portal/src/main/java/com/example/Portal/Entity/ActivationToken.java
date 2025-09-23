package com.example.Portal.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ActivationToken {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    @SequenceGenerator(name = "token_seq", sequenceName = "token_seq", allocationSize = 1)
    @Column(name = "id", precision = 19, scale = 0, nullable = false) // NUMBER(19,0)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id",
            nullable = false,
            columnDefinition = "NUMBER(19,0)" // ✅ enforce Oracle compatibility
    )
    @JsonBackReference
    private Employee employee;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}

