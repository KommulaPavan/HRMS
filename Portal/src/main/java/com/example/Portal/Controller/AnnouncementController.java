package com.example.Portal.Controller;

import com.example.Portal.Dto.AnnouncementDto;
import com.example.Portal.Dto.AnnouncementResponse;
import com.example.Portal.Repository.AnnouncementRepository;
import com.example.Portal.Service.AnnouncementMapper;
import com.example.Portal.Service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AnnouncementController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    AnnouncementRepository announcementRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createJson(
            @RequestBody AnnouncementDto dto) {
        String saved = announcementService.createAnnouncement(dto, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/api/announcements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMultipart(
            @RequestPart("announcement") AnnouncementDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        String saved = announcementService.createAnnouncement(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/api/announcements")
    public ResponseEntity<List<AnnouncementResponse>> list(
            @RequestParam(name = "includeBytes", defaultValue = "false") boolean includeBytes) {
        var all = announcementRepository.findAllByOrderByPublishAtDesc();
        return ResponseEntity.ok(AnnouncementMapper.toResponseList(all, includeBytes));
    }

    @GetMapping("/api/announcements/{id}")
    public ResponseEntity<AnnouncementResponse> getOne(
            @PathVariable long id,
            @RequestParam(name = "includeBytes", defaultValue = "true") boolean includeBytes) {
        var a = announcementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(AnnouncementMapper.toResponse(a, includeBytes));
    }

}
