package com.example.Portal.Entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "ANNOUNCEMENTS",
        indexes = { @Index(name = "IDX_ANN_PUBLISH_AT", columnList = "PUBLISH_AT") }
)
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ann_seq")
    @SequenceGenerator(name = "ann_seq", sequenceName = "ANNOUNCEMENT_SEQ", allocationSize = 1)
    @Column(name = "ID", precision = 19, scale = 0, nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY", length = 20)
    private Priority priority;

    // Audience as Set<String> -> separate table
    @ElementCollection
    @CollectionTable(
            name = "ANNOUNCEMENT_AUDIENCE",
            joinColumns = @JoinColumn(name = "ANNOUNCEMENT_ID")
    )
    @Column(name = "ROLE", length = 50, nullable = false)
    private Set<String> audience = new HashSet<>();

    @Column(name = "PUBLISH_AT")
    private Instant publishAt;

    @Column(name = "EXPIRE_AT")
    private Instant expireAt;

    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @Column(name = "CREATED_BY", length = 150)
    private String createdBy;

    @Column(name = "CREATOR_ROLE", length = 50)
    private String creatorRole;

    // 1:1 attachment (shared PK)
    @OneToOne(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AnnouncementAttachment attachment;

    public void setAttachment(AnnouncementAttachment att) {
        this.attachment = att;
        if (att != null) att.setAnnouncement(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Set<String> getAudience() {
        return audience;
    }

    public void setAudience(Set<String> audience) {
        this.audience = audience;
    }

    public Instant getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Instant publishAt) {
        this.publishAt = publishAt;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Instant expireAt) {
        this.expireAt = expireAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatorRole() {
        return creatorRole;
    }

    public void setCreatorRole(String creatorRole) {
        this.creatorRole = creatorRole;
    }

    public AnnouncementAttachment getAttachment() {
        return attachment;
    }
}
