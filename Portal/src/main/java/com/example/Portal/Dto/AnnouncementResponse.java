package com.example.Portal.Dto;

import java.util.List;

public class AnnouncementResponse {
    private Long id;
    private String title;
    private String message;
    private String priority;       // NORMAL | HIGH | CRITICAL
    private List<String> audience; // roles
    private String publishAt;      // ISO
    private String expireAt;       // ISO
    private String createdAt;      // ISO
    private String createdBy;
    private String creatorRole;
    private String status;         // LIVE | SCHEDULED | EXPIRED
    private AttachmentResponse attachment;

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public List<String> getAudience() { return audience; }
    public void setAudience(List<String> audience) { this.audience = audience; }

    public String getPublishAt() { return publishAt; }
    public void setPublishAt(String publishAt) { this.publishAt = publishAt; }

    public String getExpireAt() { return expireAt; }
    public void setExpireAt(String expireAt) { this.expireAt = expireAt; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getCreatorRole() { return creatorRole; }
    public void setCreatorRole(String creatorRole) { this.creatorRole = creatorRole; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public AttachmentResponse getAttachment() { return attachment; }
    public void setAttachment(AttachmentResponse attachment) { this.attachment = attachment; }
}
