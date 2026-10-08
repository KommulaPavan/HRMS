package com.example.Portal.Controller;


import com.example.Portal.Dto.HrEmployeeRequest;
import com.example.Portal.Service.HrEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HrEmployeeController {

    @Autowired
    HrEmployeeService hrEmployeeService;
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/api/hr/employees")
    public List<HrEmployeeRequest> getEmployee(){
        return  hrEmployeeService.getHrAllEmployee();
    }
}
