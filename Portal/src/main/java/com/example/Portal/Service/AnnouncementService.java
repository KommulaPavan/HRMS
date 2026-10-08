package com.example.Portal.Service;


import com.example.Portal.Dto.AnnouncementDto;
import com.example.Portal.Dto.AnnouncementResponse;
import com.example.Portal.Entity.Announcement;
import com.example.Portal.Entity.AnnouncementAttachment;
import com.example.Portal.Entity.Priority;
import com.example.Portal.Repository.AnnouncementRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.Portal.Service.AnnouncementMapper.toResponse;

@Service
public class AnnouncementService {

    private static final long MAX_ATTACHMENT_BYTES = 5L * 1024 * 1024; // 5 MB
    private final AnnouncementRepository announcementRepository;



    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Transactional
    public String createAnnouncement(AnnouncementDto dto, MultipartFile file) {
        Announcement a = new Announcement();
        Set<String> audience =
                dto.getAudience() == null ? Collections.emptySet()
                        : new HashSet<>(dto.getAudience());
        a.setTitle(dto.getTitle());
        a.setMessage(dto.getMessage());
        a.setPriority(parsePriority(dto.getPriority()));
        a.setAudience(safeAudience(audience));
        a.setPublishAt(dto.getPublishAt());
        a.setExpireAt(dto.getExpireAt());
        a.setCreatedAt(dto.getCreatedAt());
        a.setCreatedBy(dto.getCreatedBy());
        a.setCreatorRole(dto.getCreatorRole());

        if (file != null && !file.isEmpty()) {
            if (file.getSize() > MAX_ATTACHMENT_BYTES) {
                throw new IllegalArgumentException("Attachment too large");
            }
            try {
                AnnouncementAttachment att = new AnnouncementAttachment();
                att.setName(file.getOriginalFilename());
                att.setContentType(file.getContentType());
                att.setFileSize(file.getSize());// BLOB payload
                att.setBytes(file.getBytes());
                // keep both sides in sync (Attachment is the owning side)
                att.setAnnouncement(a);
                a.setAttachment(att);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read uploaded file", e);
            }
        }

        announcementRepository.saveAndFlush(a);
        return "Announcement created with id=" + a.getId();
    }

    private Priority parsePriority(String p) {
        String val = (p == null || p.isBlank()) ? "NORMAL" : p.trim().toUpperCase();
        try {
            return Priority.valueOf(val);
        } catch (IllegalArgumentException ex) {
            return Priority.NORMAL;
        }
    }

    private Set<String> safeAudience(Set<String> audience) {
        return audience == null ? new HashSet<>() : new HashSet<>(audience);
    }

    public static List<AnnouncementResponse> toResponseList(List<Announcement> list, boolean includeBytes) {
        return list == null ? List.of() : list.stream()
                .map(a -> toResponse(a, includeBytes))
                .toList();
    }


}
