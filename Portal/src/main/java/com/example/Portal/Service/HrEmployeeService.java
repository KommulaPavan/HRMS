package com.example.Portal.Service;


import com.example.Portal.Dto.HrEmployeeRequest;
import com.example.Portal.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HrEmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<HrEmployeeRequest> getHrAllEmployee(){
        return employeeRepository.findAll().stream().map(HrEmployeeRequest::new).collect(Collectors.toList());
    }
}
