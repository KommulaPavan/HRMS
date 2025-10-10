package com.example.Portal.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ANNOUNCEMENT_ATTACHMENT")
public class AnnouncementAttachment {

    @Id
    @Column(name = "ID", precision = 19, scale = 0, nullable = false)
    private Long id; // shared PK == announcement.id

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID", nullable = false) // FK to ANNOUNCEMENTS(ID)
    private Announcement announcement;

    @Column(name = "FILE_NAME", length = 255)
    private String name;

    @Column(name = "CONTENT_TYPE", length = 100)
    private String contentType;

    @Column(name = "FILE_SIZE")           // was "size" -> rename
    private Long fileSize;

    @Lob
    // You can also add: @JdbcTypeCode(SqlTypes.BLOB) (Hibernate 6) if you want to be explicit
    @Column(name = "BYTES")               // was "data" -> rename
    private byte[] bytes;

    // getters/setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}


