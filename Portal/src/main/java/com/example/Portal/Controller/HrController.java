package com.example.Portal.Controller;

import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Entity.Leave;
import com.example.Portal.Service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HrController {
    @Autowired
    HrService hrService;

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/hr/leave/inbox")
    public List<LeaveRequest> getLeaveApporval(){
        return hrService.hrIndox();
    }

    @PreAuthorize("hasRole('HR')")
    @PutMapping("/hr/leave/process/{id}")
    public ResponseEntity<?> approveLeave(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String status = body.get("status");
        String result = hrService.hrapprove(id, status);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PreAuthorize("hasRole('HR')")
    @GetMapping("hr/leave/history")
    public List<LeaveRequest> getLeaveHistory(){
        return hrService.getleaveHistoryHr();
    }


}
