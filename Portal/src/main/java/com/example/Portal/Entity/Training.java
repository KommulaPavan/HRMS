package com.example.Portal.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "trainings", indexes = {
        @Index(name = "ux_training_training_id", columnList = "trainingId", unique = true)
})
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB PK

    /** Public stable ID used by the API/UI */
    @Column(nullable = false, unique = true, length = 64)
    private String trainingId;

    @Column(nullable = false)
    private String title;

    private String topic;

    @Column(length = 2000)
    private String description;

    /** "Online" | "Onsite" (free text allowed) */
    private String modality;

    private Integer capacity;

    /** Kept as locationAt to match your UI normalizer */
    private String locationAt;

    private String instructor;

    /** Local date of the training */
    private LocalDate dateAt;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() { updatedAt = OffsetDateTime.now(); }

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrainingEnrollment> enrollments = new LinkedHashSet<>();

    // --- getters/setters ---
    public Long getId() { return id; }
    public String getTrainingId() { return trainingId; }
    public void setTrainingId(String trainingId) { this.trainingId = trainingId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getModality() { return modality; }
    public void setModality(String modality) { this.modality = modality; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public String getLocationAt() { return locationAt; }
    public void setLocationAt(String locationAt) { this.locationAt = locationAt; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public LocalDate getDateAt() { return dateAt; }
    public void setDateAt(LocalDate dateAt) { this.dateAt = dateAt; }
    public OffsetDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<TrainingEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<TrainingEnrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
