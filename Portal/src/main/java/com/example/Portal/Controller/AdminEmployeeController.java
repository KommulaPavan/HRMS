package com.example.Portal.Controller;


import com.example.Portal.Dto.EmployeeResponse;
import com.example.Portal.Service.ManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class AdminEmployeeController {

    @Autowired
    ManageUserService manageUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/api/admin/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeResponse> getEmployee(){
        return manageUserService.getAllEmployees();
    }
}
