package com.example.Portal.Controller;

import com.example.Portal.Dto.EmployeeProfileDto;
import com.example.Portal.Service.EmployeeProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employee")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    // Owner OR Admin/HR can view
    @PreAuthorize("#employeeId == principal.employeeId or hasAnyRole('ADMIN','HR')")
    @GetMapping("/{employeeId}")
    public EmployeeProfileDto getEmployee(@PathVariable String employeeId) {
        return employeeProfileService.updateProfile(employeeId);
    }

    // Owner OR Admin/HR can update
    @PreAuthorize("#employeeId == principal.employeeId or hasAnyRole('ADMIN','HR')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateProfile(
            @PathVariable String employeeId,
            @RequestBody EmployeeProfileDto dto
            ) {
        dto.setEmployeeId(employeeId);                           // enforce path/body consistency
        String msg = employeeProfileService.updateProfile(dto,null); // JSON variant (no MultipartFile)
        return ResponseEntity.ok(msg);
    }
}
