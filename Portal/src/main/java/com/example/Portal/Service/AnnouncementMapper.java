package com.example.Portal.Service;


import com.example.Portal.Dto.AnnouncementResponse;
import com.example.Portal.Dto.AttachmentResponse;
import com.example.Portal.Entity.Announcement;
import com.example.Portal.Entity.AnnouncementAttachment;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class AnnouncementMapper {
    private AnnouncementMapper() {}
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT;
    private static String iso(Instant i) { return i == null ? null : ISO.format(i); }

    private static String status(Instant pub, Instant exp) {
        Instant now = Instant.now();
        if (pub != null && pub.isAfter(now)) return "SCHEDULED";
        if (exp != null && exp.isBefore(now)) return "EXPIRED";
        return "LIVE";
    }

    public static AnnouncementResponse toResponse(Announcement a, boolean includeBytes) {
        if (a == null) return null;
        AnnouncementResponse dto = new AnnouncementResponse();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setMessage(a.getMessage());
        dto.setPriority(a.getPriority() != null ? a.getPriority().name() : null);

        List<String> aud = new ArrayList<>(a.getAudience() != null ? a.getAudience() : List.of());
        aud.sort(Comparator.naturalOrder());
        dto.setAudience(aud);

        dto.setPublishAt(iso(a.getPublishAt()));
        dto.setExpireAt(iso(a.getExpireAt()));
        dto.setCreatedAt(iso(a.getCreatedAt()));
        dto.setCreatedBy(a.getCreatedBy());
        dto.setCreatorRole(a.getCreatorRole());
        dto.setStatus(status(a.getPublishAt(), a.getExpireAt()));

        AnnouncementAttachment att = a.getAttachment();
        if (att != null) {
            AttachmentResponse ar=new AttachmentResponse();

            ar.setName(att.getName());
            ar.setContentType(att.getContentType());
            ar.setSize(att.getFileSize());
            if (includeBytes) {
                ar.setData(att.getBytes()); // <— byte[] (Base64 in JSON)
            }
           dto.setAttachment(ar);
        }
        return dto;
    }

    public static List<AnnouncementResponse> toResponseList(List<Announcement> list, boolean includeBytes) {
        return list == null ? List.of() : list.stream()
                .map(a -> toResponse(a, includeBytes))
                .toList();
    }
}
