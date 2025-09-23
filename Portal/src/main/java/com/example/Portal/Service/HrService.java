package com.example.Portal.Service;

import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Entity.Leave;
import com.example.Portal.Repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HrService {

    @Autowired
    LeaveRepository leaveRepository;

    public List<LeaveRequest> hrIndox(){
        return leaveRepository.findAll().stream().map(LeaveRequest::new).collect(Collectors.toList());
    }

    public String hrapprove(Long id,String status){

        Leave leave=leaveRepository.findById(id).orElseThrow(()->new RuntimeException("Id is not found"));
        leave.setStatus(status);
        leaveRepository.save(leave);
        return "leave is approved";

    }

    public List<LeaveRequest> getleaveHistoryHr(){
        return leaveRepository.findAll().stream().map(LeaveRequest::new).collect(Collectors.toList());
    }

}
