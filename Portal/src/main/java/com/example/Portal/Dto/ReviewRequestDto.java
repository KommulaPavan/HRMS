package com.example.Portal.Dto;

import com.example.Portal.Entity.Reviews;

import java.time.LocalDate;

public class ReviewRequestDto {

    private LocalDate period;
    private String employeeId;
    private int rating;
    private String goals;
    private String comments;
    private String reviewer;
    private String status;
    private LocalDate reviewDate;

    public static ReviewRequestDto toDto(Reviews review){

        ReviewRequestDto dto = new ReviewRequestDto();

        //dto.setId(review.getId());
        dto.setEmployeeId(review.getEmployeeId());
        dto.setPeriod(review.getPeriod());
        dto.setRating(review.getRating());
        dto.setGoals(review.getGoals());
        dto.setComments(review.getComments());
        dto.setReviewer(review.getReviewer());
        dto.setStatus(review.getStatus());
        dto.setReviewDate(review.getReviewDate());

        return dto;
    }

    public static Reviews toEntity(ReviewRequestDto dto){

        Reviews review = new Reviews();

        review.setEmployeeId(dto.getEmployeeId());
        review.setPeriod(dto.getPeriod());
        review.setRating(dto.getRating());
        review.setGoals(dto.getGoals());
        review.setComments(dto.getComments());
        review.setReviewer(dto.getReviewer());
        review.setStatus(dto.getStatus());
        review.setReviewDate(dto.getReviewDate());

        return review;
    }
    public LocalDate getPeriod() {
        return period;
    }

    public void setPeriod(LocalDate period) {
        this.period = period;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
