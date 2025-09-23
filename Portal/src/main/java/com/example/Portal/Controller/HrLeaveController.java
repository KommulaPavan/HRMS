package com.example.Portal.Controller;

import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Service.HrLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HrLeaveController {
    @Autowired
    HrLeaveService hrLeaveService;
    @PreAuthorize("hasRole('HR')")
    @GetMapping()
    public List<LeaveRequest> getAllLeaves(){
        return hrLeaveService.getLeaveHistory();
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/{id}/approve")

    public ResponseEntity<LeaveRequest> approve(
            @PathVariable("id") String leaveId,
            @RequestBody(required = false) LeaveRequest body,
            Authentication auth
    ) {
        // Extract approver identity (adapt this to your Security config)
        String approverEmployeeId = auth != null ? auth.getName() : "HR";
        String approverName = (String) (auth != null ? auth.getPrincipal() : "HR");
        // If you store richer UserDetails, map accordingly.

        String remark = body != null ? body.getRemark() : null;

        LeaveRequest dto = hrLeaveService.getApproval(leaveId, approverEmployeeId, approverName, remark);
        return ResponseEntity.ok(dto);
    }
}
