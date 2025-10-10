package com.example.Portal.Service;

import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Entity.Leave;
import com.example.Portal.Repository.LeaveRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class HrLeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    public List<LeaveRequest> getLeaveHistory(){
        return leaveRepository.findAll().stream().map(LeaveRequest::new).collect(Collectors.toList());
    }

    @Transactional
    public LeaveRequest getApproval(String leaveId, String approver, String remark) {
        Leave leave = leaveRepository.findByLeaveId(leaveId)
                .orElseThrow(() -> new NoSuchElementException("No leave found: " + leaveId));

        if ("APPROVED".equalsIgnoreCase(leave.getStatus())) {
            throw new IllegalStateException("Already approved");
        }
        if ("REJECTED".equalsIgnoreCase(leave.getStatus())) {
            throw new IllegalStateException("Cannot approve a rejected leave");
        }

        leave.setStatus("APPROVED");                 // ✅ not "Pending"
        leave.setApprover(approver);
        if (remark != null && !remark.isBlank()) leave.setRemark(remark);
        leave.setApprovedAt(LocalDateTime.now());

        Leave saved = leaveRepository.save(leave);
        return new LeaveRequest(saved);              // DTO ctor you already have
    }

}
