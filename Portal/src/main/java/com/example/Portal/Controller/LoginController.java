package com.example.Portal.Controller;

import com.example.Portal.Dto.EmployeeRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Role;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Service.LoginService;
import com.example.Portal.Utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/auth") // <-- base path for all auth endpoints
public class LoginController {

    private final LoginService loginService;
    private final JwtUtils jwtUtils;
    private final EmployeeRepository employeeRepository;

    public LoginController(LoginService loginService, JwtUtils jwtUtils, EmployeeRepository employeeRepository) {
        this.loginService = loginService;
        this.jwtUtils = jwtUtils;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> userLogin(@RequestBody EmployeeRequest employeeRequest) {
        String token = loginService.loginEmployee(employeeRequest);
        return ResponseEntity.ok(new TokenResponse("Bearer", token));
    }

    @GetMapping("/me") // <-- correct path; resolves to /api/auth/me
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }
        Employee emp = (Employee) authentication.getPrincipal();

        List<String> roles = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new MeResponse(
                emp.getId(),
                emp.getEmployeeId(),   // ✅ guaranteed
                emp.getEmail(),
                emp.getName(),
                roles,
                emp.getAccountStatus()
        ));
    }


    // ---- DTOs ----

    public record TokenResponse(String type, String token) {}

    public record MeResponse(
            Long id,
            String employeeId,
            String email,
            String name,
            List<String> roles,
            String accountStatus
    ) {}
}
