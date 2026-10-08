package com.example.Portal.Dto;



import com.example.Portal.Entity.Training;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class TrainingRequestDto {


    private String title;

    private String topic;
    private String description;
    private String modality;

    private Integer capacity;
    private String locationAt;
    private String instructor;
    private LocalDate dateAt;

    /** Public stable id used by the UI */
    private String trainingId;

    public TrainingRequestDto() {}

    public TrainingRequestDto(Training t) {
        this.title = t.getTitle();
        this.topic = t.getTopic();
        this.description = t.getDescription();
        this.modality = t.getModality();
        this.capacity = t.getCapacity();
        this.locationAt = t.getLocationAt();
        this.instructor = t.getInstructor();
        this.dateAt = t.getDateAt();
        this.trainingId = t.getTrainingId();
    }

    /** Alias so frontend reads `id` directly */
    @JsonProperty("id")
    public String getId() { return trainingId; }

    // --- getters/setters ---
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
    public String getTrainingId() { return trainingId; }
    public void setTrainingId(String trainingId) { this.trainingId = trainingId; }

    /** Apply PATCH-style updates onto an entity */
    public void applyTo(Training t) {
        if (this.title != null) t.setTitle(this.title);
        if (this.topic != null) t.setTopic(this.topic);
        if (this.description != null) t.setDescription(this.description);
        if (this.modality != null) t.setModality(this.modality);
        if (this.capacity != null) t.setCapacity(this.capacity);
        if (this.locationAt != null) t.setLocationAt(this.locationAt);
        if (this.instructor != null) t.setInstructor(this.instructor);
        if (this.dateAt != null) t.setDateAt(this.dateAt);
    }
}
