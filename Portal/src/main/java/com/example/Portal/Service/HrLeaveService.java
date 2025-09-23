package com.example.Portal.Service;

import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Entity.Leave;
import com.example.Portal.Repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HrLeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    public List<LeaveRequest> getLeaveHistory(){
        return leaveRepository.findAll().stream().map(LeaveRequest::new).collect(Collectors.toList());
    }

    public String getApproval(LeaveRequest leaveRequest,String leaveId,String approver){
        Leave leave=leaveRepository.findByLeaveId(leaveId).orElseThrow(()->new RuntimeException("No leave found"));
        leave.setStatus("Pending");
        leave.setLeaveId(leaveRequest.getLeaveId());
        leave.setRemark(leaveRequest.getRemark());
        leave.setApprover(leaveRequest.getApprover());
        leave.setApprovedAt(LocalDateTime.now());
        return "Leave is approved";
    }
}
