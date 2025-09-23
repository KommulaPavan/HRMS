package com.example.Portal.Service;

import com.example.Portal.Dto.LeaveRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Leave;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    EmployeeRepository employeeRepository;


    public Leave applyLeave(LeaveRequest leaveRequest,Employee emp){
        Leave leave=new Leave();
        leave.setName(emp.getName());
        leave.setTypeOf(leaveRequest.getTypeOf());
        leave.setFromDate(leaveRequest.getFromDate());
        leave.setToDate(leaveRequest.getToDate());
        leave.setDays(leaveRequest.getDays());
        leave.setReason(leaveRequest.getReason());
        leave.setStatus("PENDING"); // always set status yourself
       // leave.setCreatedAt(LocalDateTime.now());
        if(leave.getLeaveId()== null){
            String generated="LV-"+System.currentTimeMillis();
            leave.setLeaveId(generated);
        }
        leave.setEmployee(emp);
        System.out.println("Employee ID: " + emp.getId());
        System.out.println("Employee object: " + emp);
        System.out.println("Leave before save: " + leave);


        leaveRepository.save(leave);
        return leave;
    }

    /*public List<Leave> getLeaveHistory(String email) {
        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return leaveRepository.findByEmployee(emp);
    }*/

    public List<LeaveRequest> getLeaveHistory(){
        return leaveRepository.findAll().stream().map(LeaveRequest::new).collect(Collectors.toList());
    }





}
