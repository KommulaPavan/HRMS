package com.example.Portal.Controller;


import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Service.HrLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HrLeaveController {
    @Autowired
    HrLeaveService hrLeaveService;
    @PreAuthorize("hasRole('HR','ADMIN')")
    @GetMapping("/api/hr/leaves")
    public List<LeaveRequest> getAllLeaves(){
        return hrLeaveService.getLeaveHistory();
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/api/hr/leaves/{leaveId}/approve")

    public ResponseEntity<LeaveRequest> approve(
            @PathVariable String leaveId,
            @RequestBody(required = false) Map<String, String> body,
            Authentication auth
    ) {
        String approverName = (auth != null) ? String.valueOf(auth.getPrincipal()) : "HR";
        String remark = (body != null) ? body.getOrDefault("remark", null) : null;

        LeaveRequest dto = hrLeaveService.getApproval(leaveId, approverName, remark);
        return ResponseEntity.ok(dto);
    }

}
