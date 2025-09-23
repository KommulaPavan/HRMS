package com.example.Portal.Dto;

import com.example.Portal.Entity.Outbox;

import java.time.Instant;
import java.time.LocalDateTime;

public class OutboxDTO {


    private Long id;
    private String toEmail;
    private String subject;
    private String body;
    private String typeOf;
    private String link;
    private LocalDateTime createAt;
        public OutboxDTO(Outbox o){
            this.id=o.getId();
            this.toEmail=o.getToEmail();
            this.body=o.getBody();
            this.link=o.getLink();
            this.subject=o.getSubject();
            this.typeOf=o.getTypeOf();
            this.createAt=o.getCreateAt();
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
