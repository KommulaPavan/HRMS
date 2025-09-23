package com.example.Portal.Controller;

import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Leave;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Service.EmployeeService;
import com.example.Portal.Service.LeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LeaveController {

    @Autowired
    LeaveService leaveService;
    @Autowired
    EmployeeRepository employeeRepository;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/api/leaves")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequest leaveRequest, Authentication authentication,String employeeId) {

        final String principal = authentication.getName(); // could be employeeId or email

        // Try employeeId first; if not found, try email
        Employee employee = employeeRepository.findByEmployeeId(principal)
                .orElseGet(() -> employeeRepository.findByEmail(principal)
                        .orElseThrow(() -> new RuntimeException("Employee not found for principal: " + principal)));

        // Apply leave
        Leave leave = leaveService.applyLeave(leaveRequest, employee);

        Map<String, Object> response = new HashMap<>();
        response.put("employee", employee);
        response.put("leave", leave);
        response.put("message", "Leave applied successfully");
        return ResponseEntity.ok(response);
    }

    /*@PreAuthorize("hasAnyRole('EMPLOYEE', 'HR', 'ADMIN')")
    @GetMapping("/api/leaves/me")
    public List<LeaveRequest> fetchAllLeaves(Authentication authentication){
        String employeeId=authentication.getName();
       return leaveService.getLeaveHistory();*/

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/leaves/me")
    public ResponseEntity<?> fetchMyLeaves(Authentication authentication) {
        final String principal = authentication.getName();

        // same resolution rule as above to avoid mismatches
        String employeeId = employeeRepository.findByEmployeeId(principal)
                .map(Employee::getEmployeeId)
                .orElseGet(() -> employeeRepository.findByEmail(principal)
                        .map(Employee::getEmployeeId)
                        .orElseThrow(() -> new RuntimeException("Employee not found for principal: " + principal)));

        // return whatever your frontend expects: List<Leave> or Page<LeaveDto>
        return ResponseEntity.ok(leaveService.getLeaveHistory());
    }

    


   /* public List<Leave> fetchAllLeaves(Authentication auth) {
        String email = auth.getName(); // email from JWT
        System.out.println("User: " + email);
        System.out.println("Authorities: " + auth.getAuthorities());
        return leaveService.getLeaveHistory(email);
    }*/


}
