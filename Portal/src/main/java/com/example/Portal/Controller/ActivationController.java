package com.example.Portal.Controller;

import com.example.Portal.Entity.ActivationToken;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Service.TokenActivation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/activate")
public class ActivationController {

    @Autowired
    TokenActivation tokenActivation;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/{token}")
    public ResponseEntity<Map<String, Object>> verify(@PathVariable String token) {
        ActivationToken activationToken = tokenActivation.validateToken(token); // throws if invalid/expired
        Employee employee = activationToken.getEmployee();
        return ResponseEntity.ok(Map.of(
                "ok", true,
                "email", employee.getEmail(),
                "employeeId", employee.getEmployeeId()
        ));
    }


    @PostMapping("/{token}")
    public ResponseEntity<Map<String, Object>> activateEmployee(
            @PathVariable String token,
            @RequestBody Map<String, String> payload) {

        String password = payload.get("password");

        ActivationToken activationToken = tokenActivation.validateToken(token);
        Employee employee = activationToken.getEmployee();

        employee.setPassword(passwordEncoder.encode(password));
        employee.setAccountStatus("ACTIVE");
        employeeRepository.save(employee);


        tokenActivation.deleteToken(employee);


        return ResponseEntity.ok(Map.of(
                "ok", true,
                "message", "Account activated successfully",
                "status", employee.getAccountStatus()
        ));

    }



}
