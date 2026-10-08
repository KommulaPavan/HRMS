package com.example.Portal.Controller;


import com.example.Portal.Dto.OutboxDTO;
import com.example.Portal.Service.OutboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OutboxController {

    @Autowired
    OutboxService outboxService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/outbox/emails")
    public List<OutboxDTO> getAll(){
        return outboxService.getOutboxservice();

    }
}
