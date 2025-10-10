package com.example.Portal.Dto;

import java.util.List;

public class EnrollResponse {
    private String trainingId;
    private List<String> enrolledIds;
    private List<String> alreadyEnrolledIds;
    private List<String> skippedIds;

    public EnrollResponse() {}

    public EnrollResponse(String trainingId,
                          List<String> enrolledIds,
                          List<String> alreadyEnrolledIds,
                          List<String> skippedIds) {
        this.trainingId = trainingId;
        this.enrolledIds = enrolledIds;
        this.alreadyEnrolledIds = alreadyEnrolledIds;
        this.skippedIds = skippedIds;
    }

    public String getTrainingId() { return trainingId; }
    public void setTrainingId(String trainingId) { this.trainingId = trainingId; }
    public List<String> getEnrolledIds() { return enrolledIds; }
    public void setEnrolledIds(List<String> enrolledIds) { this.enrolledIds = enrolledIds; }
    public List<String> getAlreadyEnrolledIds() { return alreadyEnrolledIds; }
    public void setAlreadyEnrolledIds(List<String> alreadyEnrolledIds) { this.alreadyEnrolledIds = alreadyEnrolledIds; }
    public List<String> getSkippedIds() { return skippedIds; }
    public void setSkippedIds(List<String> skippedIds) { this.skippedIds = skippedIds; }
}
