package com.example.Portal.Controller;

import com.example.Portal.Dto.AdminAttandanceDto;
import com.example.Portal.Service.AdminAttandanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminAttadanceController {

    @Autowired
    AdminAttandanceService attandanceService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/attendance/reports")
    public List<AdminAttandanceDto> getatta(){
        return attandanceService.getattandanceAdmin();
    }


}
