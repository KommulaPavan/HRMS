package com.example.Portal.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentResponse {
    private Long id;
    private String name;
    private String contentType;
    private Long size;

    // Will be Base64 in JSON
    private byte[] data;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
}
