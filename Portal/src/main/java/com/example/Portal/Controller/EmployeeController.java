package com.example.Portal.Controller;

import com.example.Portal.Dto.EmployeeRequest;
import com.example.Portal.Dto.EmployeeResponse;
import com.example.Portal.Dto.MailMessageDAO;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Service.EmployeeService;
import com.example.Portal.Service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    SequenceService sequenceService;


   /* @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/employees/new")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest employeeRequest){
        String Response=employeeService.CreateEmployee(employeeRequest);
        return ResponseEntity.ok(Response);
    }*/

    @GetMapping("api/employees/preview-next-id")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<Map<String, String>> previewNextEmployeeId() {
        String next = sequenceService.preview();
        return ResponseEntity.ok(Map.of("nextEmployeeId", next));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/employees/invite")
    public ResponseEntity<?> createEmployeeAndSend(@RequestBody EmployeeRequest employeeRequest) {
        String response = employeeService.EmployeeMailSender(employeeRequest);
        return ResponseEntity.ok(response);
    }


//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("api/employees/preview-next-id")
//    public Map<String, String> nextId() {
//        String next = sequenceService.preview();// implement safely (or hardcode for now)
//        return Map.of("nextId", next);
//    }


    // 3. Activate employee by link
    /*@PreAuthorize("permitAll()") // allow activation without login
    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateEmployee(@PathVariable Long id) {
        String response = employeeService.activateEmployee(id);
        return ResponseEntity.ok(response);
    }*/

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    @GetMapping("/api/employees")
    public List<EmployeeResponse> getAllEmployees(

    ) {
        return employeeService.getALlEmployee();
    }




}
