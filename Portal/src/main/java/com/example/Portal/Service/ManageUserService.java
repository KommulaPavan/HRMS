package com.example.Portal.Service;


import com.example.Portal.Dto.EmployeeResponse;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageUserService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<Employee> getManageUser(){
        return employeeRepository.findAll();
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeResponse::fromEntity)   // call your static mapper
                .collect(Collectors.toList());
    }

   /* public Optional<Employee> getEmployeedata(String employeeId){
        return employeeRepository.findByEmployeeId(employeeId);
    }*/

}
