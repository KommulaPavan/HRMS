package com.example.Portal.Controller;


import com.example.Portal.Entity.Employee;
import com.example.Portal.Service.ManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManageUsersController {

    @Autowired
    ManageUserService manageUserService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<Employee> manageUsers(){
        return manageUserService.getManageUser();
    }
}
