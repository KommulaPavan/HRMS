package com.example.Portal.Controller;


import com.example.Portal.Dto.EmployeeProfileDto;
import com.example.Portal.Service.EmployeeProfileService;
import org.springframework.http.MediaType;
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
    public ResponseEntity<EmployeeProfileDto> getEmployee(@PathVariable String employeeId) {
        EmployeeProfileDto dto = employeeProfileService.getProfile(employeeId);
        return ResponseEntity.ok(dto);
    }

    // ---------- JSON PUT (no file) ----------
    // Owner OR Admin/HR can update
   // @PreAuthorize("#employeeId == principal.employeeId or hasAnyRole('ADMIN','HR')")
    @PutMapping(
            value = "/{employeeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmployeeProfileDto> updateProfileJson(
            @PathVariable String employeeId,
            @RequestBody EmployeeProfileDto dto
    ) {
        // enforce path/body consistency
        dto.setEmployeeId(employeeId);
        // call service (file == null)
        EmployeeProfileDto saved = employeeProfileService.updateProfile(dto, null);
        return ResponseEntity.ok(saved);
    }

    // ---------- Multipart PUT (with optional file) ----------
    // Owner OR Admin/HR can update
    @PreAuthorize("#employeeId == authentication.name or hasAnyRole('ADMIN','HR')")
    @PutMapping(
            value = "/{employeeId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmployeeProfileDto> updateProfileMultipart(
            @PathVariable String employeeId,
            @ModelAttribute EmployeeProfileDto dto,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        // enforce path/body consistency
        dto.setEmployeeId(employeeId);
        EmployeeProfileDto saved = employeeProfileService.updateProfile(dto, file);
        return ResponseEntity.ok(saved);
    }
}




//
//
//package com.example.Portal.Controller;
//
//import com.example.Portal.Dto.EmployeeProfileDto;
//import com.example.Portal.Service.EmployeeProfileService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/employee")
//public class EmployeeProfileController {
//
//    private final EmployeeProfileService employeeProfileService;
//
//    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
//        this.employeeProfileService = employeeProfileService;
//    }
//
//    // Owner OR Admin/HR can view
//    @PreAuthorize("#employeeId == principal.employeeId or hasAnyRole('ADMIN','HR')")
//    @GetMapping("/{employeeId}")
//    public EmployeeProfileDto getEmployee(@PathVariable String employeeId) {
//        return employeeProfileService.updateProfile(employeeId);
//    }
//
//    // Owner OR Admin/HR can update
//    @PreAuthorize("#employeeId == principal.employeeId or hasAnyRole('ADMIN','HR')")
//    @PutMapping("/{employeeId}")
//    public ResponseEntity<?> updateProfile(
//            @PathVariable String employeeId,
//            @RequestBody EmployeeProfileDto dto
//            ) {
//        dto.setEmployeeId(employeeId);                           // enforce path/body consistency
//        String msg = employeeProfileService.updateProfile(dto,file); // JSON variant (no MultipartFile)
//        return ResponseEntity.ok(msg);
//    }
//}
